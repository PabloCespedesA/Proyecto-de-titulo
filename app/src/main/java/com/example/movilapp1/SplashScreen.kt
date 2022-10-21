package com.example.movilapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        val delay:Long = 4000
        Handler(this.mainLooper).postDelayed(
            {
                val intent = Intent (this,MainActivity::class.java)
                startActivity(intent)
                finish()
            },delay
        )

    }
}