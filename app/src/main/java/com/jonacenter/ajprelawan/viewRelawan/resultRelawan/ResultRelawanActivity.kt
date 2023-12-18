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
import com.jonacenter.ajprelawan.data.RelawanData
import com.jonacenter.ajprelawan.viewRelawan.resultRelawan.adapter.ResultRelawanAdapter

class ResultRelawanActivity : AppCompatActivity(), ResultRelawanContract.View {

    private val presenter: ResultRelawanContract.Presenter by lazy {
        ResultRelawanPresenter(this)
    }

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

        // Fetch data (you may pass a specific nik for searching)
        presenter.fetchData()

        val searchEditText: EditText = findViewById(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Fetch data based on the search query
                presenter.searchByNik(s.toString().toLongOrNull())
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

    override fun showTotalCount(totalCount: Int) {
        val totalCountTextView: TextView = findViewById(R.id.totalCountTextView)
        totalCountTextView.text = "Total Relawan: $totalCount"
    }

    override fun showRelawanData(data: List<RelawanData>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val reversedData = data.reversed()
        val adapter = ResultRelawanAdapter(reversedData)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }
}

