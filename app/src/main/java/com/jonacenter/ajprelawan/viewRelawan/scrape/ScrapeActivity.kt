package com.jonacenter.ajprelawan.viewRelawan.scrape

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.api.CekDptInstance
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.DptDataResponse
import com.jonacenter.ajprelawan.viewRelawan.resultRelawan.ResultRelawanActivity

class ScrapeActivity : AppCompatActivity(), ScrapeContract.View {

    private val presenter: ScrapeContract.Presenter by lazy {
        ScrapePresenter(this, CekDptInstance.getDptInstance().create(ApiService::class.java), this)
    }

    private lateinit var edtIdCard: EditText
    private lateinit var edtNameRelawan: EditText
    private lateinit var edtNameTandeman: EditText
    private lateinit var edtNameKoordinator: EditText
    private lateinit var edtNumberPhone: EditText
    private var btnCheckdpt: TextView? = null

    val kecamatan = MutableLiveData<String>()
    val kabupaten = MutableLiveData<String>()
    val nama = MutableLiveData<String>()
    val tps = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrape)

        // Initialize UI elements

        edtIdCard = findViewById(R.id.etNik)
        edtNameRelawan = findViewById(R.id.edtNameRelawan)
        edtNameTandeman = findViewById(R.id.edtNameTandem)
        edtNameKoordinator = findViewById(R.id.edtNameKoordinator)
        edtNumberPhone = findViewById(R.id.edtNumberPhone)
        btnCheckdpt = findViewById(R.id.btncheckdpt)

        val btnInputScrape: AppCompatButton = findViewById(R.id.btnInputManualScrape)
        btnInputScrape.setOnClickListener {
            handleBtnInput()
        }
        btnCheckdpt?.setOnClickListener {
            sendDataToServer()
        }
    }

    private fun sendDataToServer() {
        val nik = edtIdCard.text.toString()
        presenter.checkData(nik)
    }

    private fun handleBtnInput() {
        val nik = edtIdCard.text.toString()
        val notps = tps.value.toString()
        val addDataRequest = AddDataRequest(
            nik = nik,
            nama = nama.value.toString(),
            nama_relawan = edtNameRelawan.text.toString(),
            koordinator = edtNameKoordinator.text.toString(),
            tandeman = edtNameTandeman.text.toString(),
            notelp = edtNumberPhone.text.toString(),
            no_tps = notps,
            kecamatan = kecamatan.value.toString(),
            kabupaten = kabupaten.value.toString()
        )
        presenter.addData(addDataRequest)
    }

    override fun showCheckDataSuccess(data: DptDataResponse) {
        kecamatan.postValue(data.kecamatan)
        kabupaten.postValue(data.kabupaten)
        nama.postValue(data.nama)
        tps.postValue(data.tps.toInt())
        Toast.makeText(this, "Check data success", Toast.LENGTH_SHORT).show()
    }

    override fun showCheckDataFailed(message: String) {
        Toast.makeText(this, "Check data failed: $message", Toast.LENGTH_SHORT).show()
    }

    override fun showAddDataSuccess() {
        Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
        // Navigate to ResultRelawanActivity
        val intent = Intent(this, ResultRelawanActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showAddDataFailed(message: String) {
        Toast.makeText(this, "Add data failed: $message", Toast.LENGTH_SHORT).show()
    }
}






