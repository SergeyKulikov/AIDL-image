package auto.atom.apphud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import auto.atom.speedometer.service.AtomParcel
import auto.atom.speedometer.service.ISpeedometerService
import auto.atom.speedometer.service.ISpeedometerServiceCallback
import auto.atom.speedometer.service.ISpeedometerServiceCallbackParcel
import auto.atom.speedometer.service.ISpeedometerServiceParcel

class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivityHUD"

    private var number: AtomParcel? = null

    /*
    private var callback = object : ISpeedometerServiceCallback.Stub(){
        override fun onSpeedChanged(speed: Float) {
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


    var aidlInterface: ISpeedometerServiceParcel? = null
    // var callbackParcel: ISpeedometerServiceCallbackParcel? = null

    private var callback = object : ISpeedometerServiceCallbackParcel.Stub(){
        override fun onParecelBallbackData(atomData: AtomParcel?) {
            val speed = atomData?.data ?: -1f
            Log.d(TAG, "onSpeedChanged: $speed")
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.d(TAG, "onServiceConnected() called with: name = $name, iBinder = $iBinder")

            aidlInterface = ISpeedometerServiceParcel.Stub.asInterface(iBinder)

            // aidlInterface?.setCallback(callback)
            // callbackParcel = ISpeedometerServiceCallbackParcel.Stub.asInterface(iBinder)
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val serviceIntent = Intent()
        serviceIntent.component = ComponentName("auto.atom.testsetvice", "auto.atom.testsetvice.SpeedometerService")


        startForegroundService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "onCreate(HUD) called with: savedInstanceState = $savedInstanceState")

        findViewById<TextView>(R.id.tvNum)?.setOnClickListener {
            (it as TextView).text = sendData()
        }
    }

    fun sendData():  String {
        number = AtomParcel().apply {
            data = (Math.random()*180).toFloat()
        }

            // var callback: AtomParcel = AtomParcel()

        aidlInterface?.setValue(number, callback)

        return number?.data.toString()
    }

}