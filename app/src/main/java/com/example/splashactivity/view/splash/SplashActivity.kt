package com.example.splashactivity.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.splashactivity.R
import com.example.splashactivity.view.base.BaseActivity
import com.example.splashactivity.view.home.HomeActivity
import com.example.splashactivity.view.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}
