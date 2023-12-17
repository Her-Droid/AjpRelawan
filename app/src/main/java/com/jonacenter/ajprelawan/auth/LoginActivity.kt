package com.jonacenter.ajprelawan.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jonacenter.ajprelawan.MainActivity
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.viewRelawan.WebViewActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var btnRegister: TextView? = null
    private var btnLogin: Button? = null
    private var database: DatabaseReference? = null
    private var progressBar: ProgressBar? = null

    // Inside your LoginActivity class
    private var checkBoxPrivacyPolicy: CheckBox? = null
    private var tvPrivacyPolicy: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnRegister = findViewById(R.id.createAccount)
        btnLogin = findViewById(R.id.btnLogin)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        progressBar = findViewById(R.id.progressBar)
        checkBoxPrivacyPolicy = findViewById(R.id.checkBoxPrivacyPolicy)
        tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicy)

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Check if the user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startMainActivity()
        }

        btnRegister?.setOnClickListener {
            val register = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(register)
        }


        // Set click listener for Privacy Policy TextView
        tvPrivacyPolicy?.setOnClickListener {
            // Launch WebViewActivity when the Privacy Policy is clicked
            val webViewIntent = Intent(applicationContext, WebViewActivity::class.java)
            startActivity(webViewIntent)
        }
        btnLogin()
    }

    private fun startMainActivity() {
        val masuk = Intent(applicationContext, MainActivity::class.java)
        startActivity(masuk)
        finish() // Optional: Close the LoginActivity to prevent going back
    }

    private fun btnLogin() {
        // Set default background and text color
        btnLogin?.setBackgroundResource(R.drawable.button_background_false)
        btnLogin?.setTextColor(ContextCompat.getColor(this, R.color.green))

        btnLogin?.setOnClickListener {
            val username = etUsername?.text.toString()
            val password = etPassword?.text.toString()
            database = FirebaseDatabase.getInstance().getReference("users")

            progressBar?.visibility = View.VISIBLE

            if (username.isEmpty() || password.isEmpty()) {
                if (username.isEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "Masukkan Username/Nomor Telephone Anda!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Masukkan Password Anda!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Hide the progress bar when username or password is empty
                progressBar?.visibility = View.GONE
                // Set background to button_background_false and textColor to white
                btnLogin?.setBackgroundResource(R.drawable.button_background_false)
                btnLogin?.setTextColor(ContextCompat.getColor(this, R.color.green))
            } else {
                // Check if the Privacy Policy checkbox is checked
                if (!checkBoxPrivacyPolicy?.isChecked!!) {
                    // If the checkbox is not checked, show a Toast or handle accordingly
                    Toast.makeText(
                        applicationContext,
                        "Harap setujui Kebijakan Privasi",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Do not proceed with login
                    progressBar?.visibility = View.GONE
                    return@setOnClickListener
                }

                // Continue with the login process...
                database!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(username).exists()) {
                            if (snapshot.child(username).child("password")
                                    .getValue(String::class.java) == password
                            ) {
                                btnLogin?.setBackgroundResource(R.drawable.button_background_true)
                                btnLogin?.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))

                                Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                // Save login state
                                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                                progressBar?.visibility = View.GONE
                                startMainActivity()
                            } else {
                                // Password is incorrect
                                Toast.makeText(
                                    applicationContext,
                                    "Password Salah",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Hide the progress bar when password is incorrect
                                progressBar?.visibility = View.GONE
                                // Set background to button_background_false and textColor to white
                                btnLogin?.setBackgroundResource(R.drawable.button_background_false)
                                btnLogin?.setTextColor(
                                    ContextCompat.getColor(
                                        this@LoginActivity,
                                        R.color.green
                                    )
                                )
                            }
                        } else {
                            // Username is incorrect
                            Toast.makeText(applicationContext, "Username Salah", Toast.LENGTH_SHORT)
                                .show()
                            // Hide the progress bar when username is incorrect
                            progressBar?.visibility = View.GONE
                            // Set background to button_background_false and textColor to white
                            btnLogin?.setBackgroundResource(R.drawable.button_background_false)
                            btnLogin?.setTextColor(
                                ContextCompat.getColor(
                                    this@LoginActivity,
                                    R.color.green
                                )
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        progressBar?.visibility = View.GONE
                    }
                })
            }
        }
    }


}

