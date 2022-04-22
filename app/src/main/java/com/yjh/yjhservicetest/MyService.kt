package com.yjh.yjhservicetest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

/*
* 1. Service的基本用法。
* 2. Activity和Service进行通信: 比如在activity中可以开始下载和查看进度，使用Binder解决。
* 3. 使用前台Service。
* */
class MyService : Service() {

    private val mBinder = DownloadBinder() //Binder用于Activity和Service进行通信

    class DownloadBinder : Binder(){ //主要是DownloadBinder类中的方法可以供activity调用

        fun startDownload(){
            Log.d("MyService", "startDownload executed")
        }

        fun getProgress(): Int{
            Log.d("MyService", "getProgress executed")
            return 0
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() { //Service第一次创建时调用
        super.onCreate()
        Log.d("MyService", "onCreate executed")

        //3. 使用前台Service-----------------
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("my_service", "前台Service通知", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "my_service")
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
            .setContentIntent(pi) //下拉并点击通知，打开NotificationActivity
            .build()
        startForeground(1, notification)
        //3. 使用前台Service------------------
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //service每次启动时调用
//        //法1：具体在Service的每个方法里开启子线程
//        thread {
//            //处理具体的逻辑
//            stopSelf()
//        }
        Log.d("MyService", "onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy executed")
    }
}