package com.example.taskmngmtapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmngmtapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)


    }
}