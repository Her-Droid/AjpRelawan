package com.jonacenter.ajprelawan.viewRelawan.resultRelawan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Intent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jonacenter.ajprelawan.MainActivity
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.viewRelawan.resultRelawan.adapter.ResultRelawanAdapter
import com.jonacenter.ajprelawan.data.RelawanData

class ResultRelawanActivity : AppCompatActivity() {

    private val viewModel by viewModels<RelawanViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Set the title to an empty string (or any other desired title)
        supportActionBar?.title = ""

        // You can also set a custom click listener for the back button if needed
        toolbar.setNavigationOnClickListener {
            // Handle the back button click
            navigateToMainActivity()
        }
        val totalCountTextView: TextView = findViewById(R.id.totalCountTextView)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Observe total count changes
        viewModel.totalCount.observe(this, Observer<Int> { totalCount ->
            totalCountTextView.text = "Total Relawan: $totalCount"
        })

        // Observe data changes
        viewModel.relawanData.observe(this, Observer<List<RelawanData>> { data ->
            // Update RecyclerView adapter with the new data
            val adapter = ResultRelawanAdapter(data)
            recyclerView.adapter = adapter
        })


        // Fetch data (you may pass a specific nik for searching)
        viewModel.fetchData()
    }


    // Handle the back button press
    override fun onBackPressed() {
        // Set onBackPressed to null
        // super.onBackPressed() is commented out to prevent default behavior
    }

    // Custom method to navigate to MainActivity
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optionally finish the current activity
    }
}
