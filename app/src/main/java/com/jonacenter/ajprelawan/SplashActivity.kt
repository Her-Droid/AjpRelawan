package com.jonacenter.ajprelawan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.jonacenter.ajprelawan.auth.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for a few seconds to show the splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Start your main activity here
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000) // Change 2000 to desired delay in milliseconds
    }
}
