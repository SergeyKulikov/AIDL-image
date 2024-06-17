package auto.atom.testapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import auto.atom.speedometer.service.AtomImage
import auto.atom.speedometer.service.AtomParcel
import auto.atom.speedometer.service.ISpeedometerService
import auto.atom.speedometer.service.ISpeedometerServiceCallback
import auto.atom.speedometer.service.ISpeedometerServiceCallbackParcel
import auto.atom.speedometer.service.ISpeedometerServiceParcel
import auto.atom.testapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private  val TAG = "MainActivity"
    val viewModel by viewModels<SpeedometerViewModel>()

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    /*
    private var callback = object : ISpeedometerServiceCallback.Stub(){
        override fun onSpeedChanged(speed: Float) {
            viewModel.currentSpeed.value = speed
            Log.d(TAG, "onSpeedChanged: $speed")
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.d(TAG, "onServiceConnected() called with: name = $name, iBinder = $iBinder")

            ISpeedometerService.Stub.asInterface(iBinder).setCallback(callback)
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }
    */

    var aidlInterface: MutableLiveData<ISpeedometerServiceParcel?> = MutableLiveData(null)
    // var callbackParcel: ISpeedometerServiceCallbackParcel? = null

    private var callback = object : ISpeedometerServiceCallbackParcel.Stub(){
        override fun onParecelBallbackData(atomData: AtomParcel?) {
            val speed = atomData?.data ?: -1f
            Log.d(TAG, "FLOAT из сервиса onSpeedChanged: $speed")
        }

        override fun onImageBack(atomImage: AtomImage?) {
            Log.d(TAG, "onImageBack: $atomImage")
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.d(TAG, "onServiceConnected() called with: name = $name, iBinder = $iBinder")

            aidlInterface.postValue(ISpeedometerServiceParcel.Stub.asInterface(iBinder))

            Log.d(TAG, "Получен AIDL interface: ${aidlInterface.value}")

            // aidlInterface?.setCallback(callback)
            // callbackParcel = ISpeedometerServiceCallbackParcel.Stub.asInterface(iBinder)
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val serviceIntent = Intent()
        serviceIntent.component = ComponentName("auto.atom.testsetvice", "auto.atom.testsetvice.SpeedometerService")

        startForegroundService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")


        aidlInterface.observeForever { itrf ->
            itrf?.let {
                /*
                serviceScope.launch {
                    while (true){
                        delay(3000)
                        // callback?.onSpeedChanged(number.toFloat())
                        it.setValue(AtomParcel().apply { data = 68.33f }, callback)
                        Log.d(TAG, "Отправляем данные")
                    }
                }
                */

                serviceScope.launch {
                    while (true){
                        delay(3000)
                        it.setValue(AtomParcel().apply { data = 11.33f }, callback)

                        // callback?.onSpeedChanged(number.toFloat())
                        val bitmap: Bitmap = resources.getDrawable(R.drawable.test_img).toBitmap()

                        val data = AtomImage(bitmap, 4)

                        it.sendImage(0, data, callback)
                        Log.d(TAG, "Отправляем данные: "+bitmap.byteCount)
                    }
                }
            }
        }



        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme


//                LaunchedEffect(Unit) {// TODO: Remowe this stub
//                    while (true){
//                        delay(1000)
//                        viewModel.currentSpeed.value = (Math.random()*180).toFloat()
//                    }
//
//                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Speedometer(speedometerViewModel = viewModel)
                }



            }
        }




    }
}

