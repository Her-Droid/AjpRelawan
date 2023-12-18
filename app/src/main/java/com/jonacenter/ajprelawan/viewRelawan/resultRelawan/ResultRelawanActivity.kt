package com.jonacenter.ajprelawan.viewRelawan.resultRelawan

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonacenter.ajprelawan.MainActivity
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.viewRelawan.resultRelawan.adapter.ResultRelawanAdapter

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

        viewModel.relawanData.observe(this) { data ->
            // Reverse the order of the data before updating the RecyclerView adapter
            val reversedData = data.reversed()

            // Update RecyclerView adapter with the reversed data
            val adapter = ResultRelawanAdapter(reversedData)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.setHasFixedSize(true)
        }


        // Fetch data (you may pass a specific nik for searching)
        viewModel.fetchData()

        val searchEditText: EditText = findViewById(R.id.searchEditText)
        // Observe changes in the searchEditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Fetch data based on the search query
                viewModel.fetchData(s.toString().toLongOrNull())
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

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
