package com.yjh.yjhservicetest

import android.app.IntentService
import android.content.Intent
import android.util.Log

/*
* 4. 使用IntentService，onHandleIntent方法直接运行在子线程中。
* */
class MyIntentService : IntentService("MyIntentService"){

    override fun onHandleIntent(intent: Intent?) {
        Log.d("MyIntentService", "Thread id is ${Thread.currentThread().name}") //打印当前线程的id
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestroy executed")
    }

}