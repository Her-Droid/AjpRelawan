package com.jonacenter.ajprelawan.viewRelawan

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.api.CekDptInstance
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.ApiResponse
import com.jonacenter.ajprelawan.data.CheckDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScrapeActivity : AppCompatActivity() {

    private lateinit var edtIdCard: EditText
    private lateinit var edtNameRelawan: EditText
    private lateinit var edtNameTandeman: EditText
    private lateinit var edtNameKoordinator: EditText
    private lateinit var edtNumberPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrape)

        edtIdCard = findViewById(R.id.etNik)
        edtNameRelawan = findViewById(R.id.edtNameRelawan)
        edtNameTandeman = findViewById(R.id.edtNameTandem)
        edtNameKoordinator = findViewById(R.id.edtNameKoordinator)
        edtNumberPhone = findViewById(R.id.edtNumberPhone)

        val btnInputScrape: AppCompatButton = findViewById(R.id.btnInputManualScrape)
        btnInputScrape.setOnClickListener {
            sendDataToServer()
        }
    }

    private val apiService by lazy {
        CekDptInstance.getDptInstance().create(ApiService::class.java)
    }

    private fun sendDataToServer() {
        val nik = edtIdCard.text.toString()

        // Call API to check data
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val checkDataResponse = apiService.checkDataDpt(nik)
                handleCheckDataResponse(checkDataResponse.body())
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }

    private fun handleCheckDataResponse(response: CheckDataResponse?) {
        when {
            response == null -> {
                // Handle null response
            }
            !response.status -> {
                // Handle unsuccessful response
                Toast.makeText(this, response.responseMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Handle successful response
                val data = response.data
                if (data != null) {
                    // Extract data and update UI
                    edtNameRelawan.setText(data.nama)
                    edtNameTandeman.setText(data.nama)
                    edtNameKoordinator.setText(data.nama)
                    edtNumberPhone.setText(data.tps)

                    // Call API to add data
                    val addDataRequest = AddDataRequest(
                        nik = data.nik.toInt(),
                        nama = data.nama,
                        nama_relawan = edtNameRelawan.text.toString(),
                        koordinator = edtNameKoordinator.text.toString(),
                        tandeman = edtNameTandeman.text.toString(),
                        notelp = edtNumberPhone.text.toString().toInt(),
                        no_tps = edtNumberPhone.text.toString().toInt(),
                        kecamatan = data.kecamatan,
                        kabupaten = data.kabupaten
                    )

                    GlobalScope.launch(Dispatchers.Main) {
                        val addDataResponse = apiService.addData(addDataRequest)
                        handleAddDataResponse(addDataResponse.body())


                    }
                } else {
                    // Handle null data in the response
                }
            }
        }
    }

    private fun handleAddDataResponse(response: ApiResponse?) {
        when {
            response == null -> {
                // Handle null response
            }
            !response.status -> {
                // Handle unsuccessful response
                Toast.makeText(this, response.responseMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Handle successful response
                Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()

                // Navigate to ResultRelawanActivity
                val intent = Intent(this, ResultRelawanActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}





