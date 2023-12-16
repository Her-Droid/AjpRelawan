package com.jonacenter.ajprelawan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.jonacenter.ajprelawan.auth.LoginActivity
import com.jonacenter.ajprelawan.viewRelawan.ManualActivity
import com.jonacenter.ajprelawan.viewRelawan.ResultRelawanActivity
import com.jonacenter.ajprelawan.viewRelawan.ScrapeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnInputManual: AppCompatButton
    private lateinit var btnInputManualScrape: AppCompatButton
    private lateinit var btnCheckResult: AppCompatButton
    private lateinit var btnLogout: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInputManual = findViewById(R.id.btnInputManual)
        btnInputManualScrape = findViewById(R.id.btnInputManualScrape)
        btnCheckResult = findViewById(R.id.btnCheckResult)
        btnLogout = findViewById(R.id.tvLogout)

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Check if the user is not logged in, redirect to LoginActivity
        if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the MainActivity to prevent going back
        }

        // Set click listener for the logout button
        btnLogout.setOnClickListener {
            // Reset login state
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the MainActivity to prevent going back
        }

        // Set click listeners for buttons
        btnInputManual.setOnClickListener {
            onButtonClicked(btnInputManual)
            val intent = Intent(this, ManualActivity::class.java)
            startActivity(intent)
        }

        btnInputManualScrape.setOnClickListener {
            onButtonClicked(btnInputManualScrape)
            val intent = Intent(this, ScrapeActivity::class.java)
            startActivity(intent)
        }

        btnCheckResult.setOnClickListener {
            onButtonClicked(btnCheckResult)
            val intent = Intent(this, ResultRelawanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onButtonClicked(button: AppCompatButton) {
        // Reset all buttons to default state
        resetButtons()

        // Set clicked button to the selected state
        button.setBackgroundResource(R.drawable.button_background_true)
        button.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun resetButtons() {
        btnInputManual.setBackgroundResource(R.drawable.button_background_false)
        btnInputManual.setTextColor(ContextCompat.getColor(this, R.color.green))

        btnInputManualScrape.setBackgroundResource(R.drawable.button_background_false)
        btnInputManualScrape.setTextColor(ContextCompat.getColor(this, R.color.green))

        btnCheckResult.setBackgroundResource(R.drawable.button_background_false)
        btnCheckResult.setTextColor(ContextCompat.getColor(this, R.color.green))
    }

}

