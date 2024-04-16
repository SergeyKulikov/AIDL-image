package auto.atom.testapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
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
import auto.atom.speedometer.service.ISpeedometerService
import auto.atom.speedometer.service.ISpeedometerServiceCallback
import auto.atom.testapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    private  val TAG = "MainActivity"
    val viewModel by viewModels<SpeedometerViewModel>()

    private var callback= object : ISpeedometerServiceCallback.Stub(){
        override fun onSpeedChanged(speed: Float) {
            viewModel.currentSpeed.value = speed
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.d(TAG, "onServiceConnected() called with: name = $name, iBinder = $iBinder")

            ISpeedometerService.Stub.asInterface(iBinder).setCallback(callback)
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

