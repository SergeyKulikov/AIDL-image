package auto.atom.testsetvice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import auto.atom.speedometer.service.AtomParcel
import auto.atom.speedometer.service.ISpeedometerServiceCallbackParcel
import auto.atom.speedometer.service.ISpeedometerServiceParcel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpeedometerService : Service() {
    private val TAG = "SpeedometerService"

    private var number: Double = 0.0

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private var callback: ISpeedometerServiceCallbackParcel? = null

    /*
    private val binder = object : ISpeedometerService.Stub() {
        override fun setCallback(callback: ISpeedometerServiceCallback?) {
            this@SpeedometerService.callback = callback
        }
    }
    */

    private val binder = object : ISpeedometerServiceParcel.Stub() {
        /*
        override fun setValue(
            incomeData: AtomParcel?,
            callback: ISpeedometerServiceCallbackParcel?
        ) {
            this@SpeedometerService.number = incomeData?.data?.toDouble() ?: -2.0
            this@SpeedometerService.callback = callback
        }

         */


        override fun setValue(incomeData: AtomParcel?, callback: ISpeedometerServiceCallbackParcel?) {
            Log.d(TAG, "--> ПОЛУЧИЛИ В СЕРВИС: "+incomeData?.data.toString())
            this@SpeedometerService.number = incomeData?.data?.toDouble() ?: -2.0

            // Log.d(TAG, "<-- ОТПРАВИЛИ ИЗ СЕРВИСА: "+(outgoingData?.data?.toDouble() ?: 3.0).toString())

            var newValue: Float = (incomeData?.data ?: 0).toFloat() + 100f
            var outgoingData = AtomParcel().apply { data = newValue }
            callback?.onParecelBallbackData(outgoingData)

            Log.d(TAG, "<-- ОТПРАВИЛИ ИЗ СЕРВИСА: "+outgoingData.data.toString())
        }
    }

    override fun onCreate() {
        // В момент создания генерим случайное число. Потом запускаем получение данных в одном
        // приложении, а потом во втором. Если у нас один экземпляр сервиса, то
        // поба приложения будут получать это число

        number = Math.random()*180
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        startForeground()

        /*
        serviceScope.launch {
            while (true){
                delay(3000)
                callback?.onSpeedChanged((Math.random()*180).toFloat())
                Log.d(TAG, "onSpeedChanged() called")
            }
        }
        */

        serviceScope.launch {
            while (true){
                delay(3000)
                // callback?.onSpeedChanged(number.toFloat())
                callback?.onParecelBallbackData(AtomParcel().apply { number })
                Log.d(TAG, "onSpeedChanged() called")
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // stopForeground(STOP_FOREGROUND_REMOVE)
        serviceScope.cancel()
    }

    private fun startForeground() {

        runCatching {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ddd")
                .setContentText("ddd")
                .build()
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }.onFailure { it.printStackTrace() }
    }


    val CHANNEL_ID = "MyForegroundServiceChannel"
    val CHANNEL_NAME = "Channel name"
    val NOTIFICATION_ID = 1

    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }


}