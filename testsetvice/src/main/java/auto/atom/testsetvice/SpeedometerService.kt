package auto.atom.testsetvice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import auto.atom.speedometer.service.ISpeedometerServiceCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpeedometerService : Service() {

    private val serviceScope = CoroutineScope()
    private var callback: ISpeedometerServiceCallback? = null

    override fun onBind(intent: Intent): IBinder = TODO()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            while (true) {
                delay(1000)
                callback?.onSpeedChanged((Math.random() * 180).toFloat())
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

    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    val notification
        get() = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Foreground speedometer service")
            .setContentText("")
            .build()

    companion object {
        val CHANNEL_ID = "ForegroundSpeedometerServiceChannel"
        val CHANNEL_NAME = "Foreground speedometer service channel"
        val NOTIFICATION_ID = 1
    }

}