package com.jonacenter.ajprelawan.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jonacenter.ajprelawan.api.Config
import com.jonacenter.ajprelawan.R

class RegisterActivity : AppCompatActivity() {

    private var etNumberPhone: EditText? = null
    private var etName: EditText? = null
    private var etPassword: EditText? = null
    private var btnRegister: Button? = null
    private var database: DatabaseReference? = null
    private var btnLogin: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNumberPhone = findViewById(R.id.etUsername)
        etName = findViewById(R.id.etName)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.loginAccount)

        // Set maximum character limit for EditText fields
        etNumberPhone?.filters = arrayOf(InputFilter.LengthFilter(15))
        etName?.filters = arrayOf(InputFilter.LengthFilter(15))
        etPassword?.filters = arrayOf(InputFilter.LengthFilter(15))

        database = FirebaseDatabase.getInstance()
            .getReferenceFromUrl(Config.DATABASE_URL)

        btnLogin?.setOnClickListener {
            val loginIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        btnRegister()

    }

    private fun btnRegister() {

        // Set default background and text color
        btnRegister?.setBackgroundResource(R.drawable.button_background_false)
        btnRegister?.setTextColor(ContextCompat.getColor(this, R.color.green))

        btnRegister?.setOnClickListener {
            val numberPhone = etNumberPhone?.text.toString()
            val name = etName?.text.toString()
            val password = etPassword?.text.toString()

            if (numberPhone.isEmpty() || name.isEmpty() || password.isEmpty()) {
                // Set background to button_background_false and textColor to white if any field is empty
                btnRegister?.setBackgroundResource(R.drawable.button_background_false)
                btnRegister?.setTextColor(ContextCompat.getColor(this, R.color.green))
                Toast.makeText(applicationContext, "Ada Data Yang Masih Kosong!!", Toast.LENGTH_SHORT).show()
            } else {
                // Set background to button_background_true and textColor to green if all fields are filled
                btnRegister?.setBackgroundResource(R.drawable.button_background_true)
                btnRegister?.setTextColor(ContextCompat.getColor(this, R.color.white))

                // Check for individual character limits
                if (numberPhone.length > 15 || name.length > 15 || password.length > 15) {
                    Toast.makeText(applicationContext, "Batas Maksimal 15 Karakter untuk Nomor HP, Nama, dan Password!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                // Check if the phone number is already registered
                checkPhoneNumberExistence(numberPhone, name, password)
            }
        }

    }


    private fun checkPhoneNumberExistence(numberPhone: String, name: String, password: String) {
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        usersRef.child(numberPhone).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Phone number already registered
                    Toast.makeText(
                        applicationContext,
                        "Nomor Telephone sudah terdaftar!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Register the new user
                    registerUser(numberPhone, name, password)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(
                    applicationContext,
                    "Error checking phone number existence",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun registerUser(numberPhone: String, name: String, password: String) {
        // Register the new user in the database
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        usersRef.child(numberPhone).child("numberPhone").setValue(numberPhone)
        usersRef.child(numberPhone).child("name").setValue(name)
        usersRef.child(numberPhone).child("password").setValue(password)

        Toast.makeText(applicationContext, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()

        val register = Intent(applicationContext, LoginActivity::class.java)
        startActivity(register)
    }
}


