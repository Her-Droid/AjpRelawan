package com.jonacenter.ajprelawan.viewRelawan

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.jonacenter.ajprelawan.api.Config
import com.jonacenter.ajprelawan.R

class ScrapeActivity : AppCompatActivity() {

    private lateinit var btnInputScrape : AppCompatButton
    private lateinit var edtIdCard : EditText
    private lateinit var edtNameRelawan : EditText
    private lateinit var edtNameTandeman : EditText
    private lateinit var edtNameKoordinator: EditText
    private lateinit var edtNumberPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrape)
        btnInputScrape = findViewById(R.id.btnInputManualScrape)
        edtIdCard = findViewById(R.id.etNik)
        edtNameRelawan = findViewById(R.id.edtNameRelawan)
        edtNameTandeman = findViewById(R.id.edtNameTandem)
        edtNameKoordinator = findViewById(R.id.edtNameKoordinator)
        edtNumberPhone = findViewById(R.id.edtNumberPhone)
    }

}
