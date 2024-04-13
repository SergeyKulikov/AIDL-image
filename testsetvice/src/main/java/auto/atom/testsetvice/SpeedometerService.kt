package auto.atom.testsetvice

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import androidx.core.app.NotificationCompat
import auto.atom.speedometer.service.ISpeedometerService
import auto.atom.speedometer.service.ISpeedometerServiceCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpeedometerService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private var callback:ISpeedometerServiceCallback? = null


    override fun onBind(intent: Intent): IBinder =  TODO()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()

        serviceScope.launch {
            while (true){
                delay(1000)
                callback?.onSpeedChanged((Math.random()*180).toFloat())
            }

        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceScope.cancel()
    }

    private fun startForeground() {
        TODO()
    }


}