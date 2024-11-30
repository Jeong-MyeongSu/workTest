package com.wjdaudtn.worktest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.wjdaudtn.worktest.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LiveData 관찰
        MyApplication.workNumberLiveData.observe(this, Observer { newStepCount ->
            binding.txtWorkNum.text = MyApplication.workNumber.toString()
        })

    }
}