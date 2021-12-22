package com.h.alamassi.library

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.h.alamassi.library.databinding.ActivitySignUpBinding
import com.h.alamassi.library.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        supportActionBar!!.hide()
        Handler().postDelayed({
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            },1500)
    }
}



