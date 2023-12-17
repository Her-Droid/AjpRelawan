package com.jonacenter.ajprelawan.viewRelawan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.api.BaseDataInstance
import com.jonacenter.ajprelawan.data.AddDataRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualActivity : AppCompatActivity() {

    private lateinit var edtNik: EditText
    private lateinit var edtName: EditText
    private lateinit var edtNameRelawan: EditText
    private lateinit var edtNameTandeman: EditText
    private lateinit var edtNameKoordinator: EditText
    private lateinit var edtNumberTps: EditText
    private lateinit var edtNumberPhone: EditText
    private lateinit var edtKecamatan: EditText
    private lateinit var edtKabupaten: EditText
    private lateinit var btnSubmitInput: AppCompatButton
    private lateinit var checkBox: CheckBox
    private val apiService = BaseDataInstance.getBaseInstance().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        edtNik = findViewById(R.id.edtNik)
        edtName = findViewById(R.id.edtName)
        edtNameRelawan = findViewById(R.id.edtNameRelawan)
        edtNameTandeman = findViewById(R.id.edtNameTandem)
        edtNameKoordinator = findViewById(R.id.edtNameKoordinator)
        edtNumberTps = findViewById(R.id.edtNumberTps)
        edtNumberPhone = findViewById(R.id.edtNumberPhone)
        edtKecamatan = findViewById(R.id.edtKecamatan)
        edtKabupaten = findViewById(R.id.edtKabupatenKota)
        checkBox = findViewById(R.id.checkBox)
        btnSubmitInput = findViewById(R.id.btnInputManual)

        edtNik.filters = arrayOf(InputFilter.LengthFilter(18))
        edtNik.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check length and show Toast if it exceeds the limit
                if (s != null && s.length > 18) {
                    showToast("Nik tidak boleh lebih dari 18 karakter")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
        edtNumberPhone.filters = arrayOf(InputFilter.LengthFilter(15))
        edtNumberPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this example
            }

            override fun afterTextChanged(s: Editable?) {
                // Check if the entered number starts with '0'
                if (!s.isNullOrEmpty() && s[0] != '0') {
                    // Show a toast message
                    Toast.makeText(applicationContext, "Masukkan nomor handphone dengan benar", Toast.LENGTH_SHORT).show()

                    // Optionally clear the input
                    // edtNumberPhone.text.clear()
                }

                // Check if the length is greater than 15
                if (s != null && s.length > 15) {
                    // Show a toast message
                    Toast.makeText(applicationContext, "Nomor handphone terlalu panjang", Toast.LENGTH_SHORT).show()

                    // Optionally truncate the input to the first 15 characters
                    // edtNumberPhone.setText(s.subSequence(0, 15))
                }
            }
        })
        edtNumberTps.filters = arrayOf(InputFilter.LengthFilter(5))
        edtNumberTps.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this example

            }

            override fun afterTextChanged(s: Editable?) {
                // Check if the length is greater than 5
                if (s != null && s.length > 5) {
                    // Show a toast message
                    Toast.makeText(applicationContext, "Nomor TPS terlalu panjang", Toast.LENGTH_SHORT).show()

                    // Optionally truncate the input to the first 15 characters
                    // edtNumberPhone.setText(s.subSequence(0, 15))
                }
            }
        })

        setButtonState()
        btnSubmit()
    }

    private fun setButtonState() {
        // Check if all specified EditTexts are not empty
        val isAllFieldsNotEmpty =
            listOf(
                edtNik, edtNameRelawan, edtNameKoordinator, edtNameTandeman,
                edtNumberPhone, edtNumberTps, edtKecamatan, edtKabupaten
            ).all { it.text.toString().isNotEmpty() }

        // Check if the entered number starts with '08' or '06'
        val isPhoneNumberValid = edtNumberPhone.text.toString().startsWith("08") || edtNumberPhone.text.toString().startsWith("06")

        // Check if the checkbox is checked
        val isCheckBoxChecked = checkBox.isChecked

        // Enable or disable btnSubmit based on the conditions
        btnSubmitInput.isEnabled = isAllFieldsNotEmpty && isPhoneNumberValid && isCheckBoxChecked

        // Set text color based on the condition
        val textColorResId =
            if (isAllFieldsNotEmpty) R.color.white
            else R.color.green

        // Set background drawable and text color based on the conditions
        val backgroundDrawable =
            if (isAllFieldsNotEmpty && isPhoneNumberValid && isCheckBoxChecked) {
                btnSubmitInput.setTextColor(ContextCompat.getColor(this, textColorResId))
                R.drawable.button_background_true
            } else {
                btnSubmitInput.setTextColor(ContextCompat.getColor(this, textColorResId))
                R.drawable.button_background_false
            }

        btnSubmitInput.setBackgroundResource(backgroundDrawable)
    }

    private fun btnSubmit() {
        btnSubmitInput.setOnClickListener {
            if (!checkBox.isChecked) {
                showToast("Centang checkbox untuk melanjutkan")
                return@setOnClickListener
            }
            // Check if any EditText is empty
            if (listOf(edtNik, edtNameRelawan, edtNameKoordinator, edtNameTandeman, edtNumberPhone, edtNumberTps, edtKecamatan, edtKabupaten)
                    .any { it.text.toString().isEmpty() }
            ) {
                showToast("Semua kolom harus diisi")
                return@setOnClickListener
            }

            // Check if the entered number starts with '08' or '06'
            val phoneNumber = edtNumberPhone.text.toString()
            if (!phoneNumber.startsWith("08") && !phoneNumber.startsWith("06")) {
                // Show a toast message
                showToast("Masukkan nomor handphone dengan benar (diawali dengan 08 atau 06)")
                return@setOnClickListener
            }

            val nik = edtNik.text.toString()
            val namaRelawan = edtNameRelawan.text.toString()
            val koordinator = edtNameKoordinator.text.toString()
            val tandeman = edtNameTandeman.text.toString()
            val notelp = edtNumberPhone.text.toString()
            val noTps = edtNumberTps.text.toString()
            val kecamatan = edtKecamatan.text.toString()
            val kabupaten = edtKabupaten.text.toString()



            val requestData = AddDataRequest(
                nik,
                namaRelawan,
                koordinator,
                tandeman,
                notelp,
                noTps,
                kecamatan,
                kabupaten
            )



            // Make the API call using Retrofit
            try {
                val call = apiService.postAddData(requestData)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        // Handle the response if needed
                        if (response.isSuccessful) {
                            // If successful, navigate to the ResultRelawanActivity
                            val intent =
                                Intent(this@ManualActivity, ResultRelawanActivity::class.java)
                            startActivity(intent)
                        } else {
                            showToast("Gagal mengirim data. Response server: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        // Handle failure
                        showToast("Error: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                // Handle exception if the request fails
                showToast("Error: ${e.message}")
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}







