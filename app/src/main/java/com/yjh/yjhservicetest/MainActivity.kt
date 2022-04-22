package com.yjh.yjhservicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button

/*
* 1. Service的基本用法。
* 2. Activity和Service进行通信: 比如在activity中可以开始下载和查看进度，使用Binder解决。
* 3. 使用前台Service。
* 4. 使用IntentService：Service默认主线程，所以要用到Android多线程技术。法1：具体在Service的每个方法里开启子线程。法2：使用IntentService
* */

class MainActivity : AppCompatActivity() {
    lateinit var downloadBinder: MyService.DownloadBinder

    private val connection = object : ServiceConnection{

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) { //activity和service成功绑定时调用
            downloadBinder = service as MyService.DownloadBinder //向下转型
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) { //service的创建进程崩溃或被杀掉时调用

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. Service的基本用法-------------------------------------------
        findViewById<Button>(R.id.startServiceBtn).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent) //启动Service
        }

        findViewById<Button>(R.id.stopServiceBtn).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent) //停止Service
        }

        //2. Activity和Service进行通信----------------------------------
        findViewById<Button>(R.id.bindServiceBtn).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE) //绑定Service
        }

        findViewById<Button>(R.id.unbindServiceBtn).setOnClickListener {
            unbindService(connection) //解绑Service
        }

        //4. 使用IntentService
        findViewById<Button>(R.id.startIntentServiceBtn).setOnClickListener {
            Log.d("MainActivity", "Thread id is ${Thread.currentThread().name}") //打印主线程的id
            val intent = Intent(this, MyIntentService::class.java)
            startService(intent) //解绑Service
        }
    }
}