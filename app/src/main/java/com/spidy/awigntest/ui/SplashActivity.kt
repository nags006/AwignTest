package com.spidy.awigntest.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.spidy.awigntest.R

class SplashActivity : AppCompatActivity()
{
    private val SPLASH_TIME_OUT:Long=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            startActivity(Intent(this,MapViewActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}