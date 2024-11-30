package com.wjdaudtn.worktest

import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.lifecycle.MutableLiveData
import kotlin.properties.Delegates

/**
 *packageName    : com.wjdaudtn.worktest
 * fileName       : MyApplication
 * author         : licen
 * date           : 2024-11-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-11-15        licen       최초 생성
 */
class MyApplication:Application() {
    companion object{
        val workNumberLiveData: MutableLiveData<Int> = MutableLiveData(0)
        var workNumber: Int = 0
            set(value) {
                field = value
                workNumberLiveData.postValue(value)
            }
    }
    override fun onCreate() {
        super.onCreate()

        val serviceIntent = Intent(this, MyService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

    }
}