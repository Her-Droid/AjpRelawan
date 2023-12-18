package com.jonacenter.ajprelawan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.jonacenter.ajprelawan.auth.LoginActivity
import com.jonacenter.ajprelawan.viewRelawan.DeleteDataActivity
import com.jonacenter.ajprelawan.viewRelawan.manual.ManualActivity
import com.jonacenter.ajprelawan.viewRelawan.resultRelawan.ResultRelawanActivity
import com.jonacenter.ajprelawan.viewRelawan.scrape.ScrapeActivity

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
        // Inside MainActivity.onCreate()

        val btnDeleteDataRequest: AppCompatButton = findViewById(R.id.btnDeleteAccount)
        btnDeleteDataRequest.setOnClickListener {
            val intent = Intent(this, DeleteDataActivity::class.java)
            startActivity(intent)
        }


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

        val btnHelpDesk: AppCompatButton = findViewById(R.id.btnHelpDesk)

        btnHelpDesk.setOnClickListener {
            openWhatsAppChat("82258821877")
        }

    }
    private fun openWhatsAppChat(phoneNumber: String) {
        val whatsappIntent = Intent(Intent.ACTION_VIEW)
        val phoneWithCountryCode = "+62$phoneNumber" // Set the country code to +62

        // Use Uri.parse with the "smsto" scheme and the phone number
        whatsappIntent.data = Uri.parse("smsto:$phoneWithCountryCode")

        // Check if there's a package that can handle this intent (WhatsApp)
        if (whatsappIntent.resolveActivity(packageManager) != null) {
            startActivity(whatsappIntent)
        } else {
            Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
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

    private fun deleteAccount() {
        // Get the user's phone number from SharedPreferences
        val phoneNumber = sharedPreferences.getString("phoneNumber", "")

        // Check if the phone number is not empty
        if (!phoneNumber.isNullOrEmpty()) {
            // Delete the user's data from the Realtime Database
            val usersRef = FirebaseDatabase.getInstance().getReference("users")
            usersRef.child(phoneNumber).removeValue()

            // Reset login state
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the MainActivity to prevent going back
        }
    }


}

