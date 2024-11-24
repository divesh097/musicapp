package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import java.util.logging.Handler

class splash : AppCompatActivity() {
    private lateinit var l: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        l = findViewById(R.id.aa)
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, music::class.java).also {
                startActivity(it)
            }
        },1500)

    }
}