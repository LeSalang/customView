package com.lesa.mycustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lesa.mycustomview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GradientView.Listener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.gradientView.listener = this
    }

    override fun onClick(index: Int, color: String) {
        binding.tvSelector.text = color
    }
}