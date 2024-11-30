package com.wjdaudtn.worktest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.properties.Delegates

class MyService : Service(),SensorEventListener {

    private lateinit var screenOnReceiver: BroadcastReceiver //스크린 ON BroadcastReceiver
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null



    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "ForegroundServiceChannel"
            val channel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW,
            )
            channel.enableVibration(false)//진동 비활성화
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "ForegroundServiceChannel")
            .setContentTitle("Foreground Service")
            .setContentText(MyApplication.workNumber.toString())
            .setSmallIcon(R.drawable.ic_launcher_background) // 반드시 필요
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true) // 사용자가 알림을 스와이프해도 제거되지 않게 설정
            .setVibrate(null) // 진동 패턴을 null로 설정
//            .setContentIntent(메인 엑티비티 paddingintent)
            .build()

        startForeground(1, notification)

        // 브로드캐스트 리시버 설정
        screenOnReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_SCREEN_ON) {
                    Log.d("BackgroundService", "Screen turned ON")
                    // 화면이 켜지면 Activity 시작
                    val activityIntent = Intent(context, ShowActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context?.startActivity(activityIntent)
                }
            }
        }
        // 화면 켜짐 감지 리시버 등록
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        registerReceiver(screenOnReceiver, filter)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        } ?: Log.e("StepCounterService", "No Step Detector Sensor found!")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // 리시버 해제
        unregisterReceiver(screenOnReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
            Log.d("StepCounterService", "Step detected!, ${MyApplication.workNumber}")
            MyApplication.workNumber++

            val notification = NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle("Foreground Service")
                .setContentText(MyApplication.workNumber.toString())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_LOW)//중요도가 높으면 진동이 울린다
                .setOngoing(true) // 사용자가 알림을 스와이프해도 제거되지 않게 설정
                .setVibrate(null) // 진동 패턴을 null로 설정
                // .setContentIntent(메인 엑티비티 paddingintent)
                .build()
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.notify(1, notification) // ID는 기존 알림과 동일하게 유지
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // 센서 정확도 변화 처리 (필요 시)
    }
}