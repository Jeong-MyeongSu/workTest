package com.wjdaudtn.worktest

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.wjdaudtn.worktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestActivityRecognitionPermission()

        // LiveData 관찰
        MyApplication.workNumberLiveData.observe(this, Observer { newStepCount ->
            binding.txtWorkNum.text = MyApplication.workNumber.toString()
        })


    }
    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    private fun requestActivityRecognitionPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    // 권한이 허용되었을 때의 처리
                    Toast.makeText(
                        this,
                        "Activity recognition permission granted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // 권한이 거부되었을 때의 처리
                    Toast.makeText(
                        this,
                        "Activity recognition permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        if (ContextCompat.checkSelfPermission(this, "android.permission.ACTIVITY_RECOGNITION")
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없을 경우 요청
            requestPermissionLauncher.launch("android.permission.ACTIVITY_RECOGNITION")
        }
    }
}