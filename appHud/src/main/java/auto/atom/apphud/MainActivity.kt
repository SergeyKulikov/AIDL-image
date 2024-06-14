package auto.atom.apphud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import auto.atom.speedometer.service.ISpeedometerService
import auto.atom.speedometer.service.ISpeedometerServiceCallback

class MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivityHUD"

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val serviceIntent = Intent()
        serviceIntent.component = ComponentName("auto.atom.testsetvice", "auto.atom.testsetvice.SpeedometerService")


        startForegroundService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "onCreate(HUD) called with: savedInstanceState = $savedInstanceState")

    }

}