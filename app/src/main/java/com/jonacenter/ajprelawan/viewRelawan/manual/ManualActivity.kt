package com.jonacenter.ajprelawan.viewRelawan.manual

import ManualPresenter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.api.BaseDataInstance
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.viewRelawan.resultRelawan.ResultRelawanActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualActivity : AppCompatActivity(), ManualContract.View {

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
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: ManualContract.Presenter
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
        progressBar = findViewById(R.id.progressBar)
        checkBox = findViewById(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, _ -> setButtonState() }
        btnSubmitInput = findViewById(R.id.btnInputManual)

        presenter = ManualPresenter(this, apiService)

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
                    showToast("Masukkan nomor handphone dengan benar (diawali dengan 08 atau 06)")

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

    private fun btnSubmit() {
        btnSubmitInput.setOnClickListener {
            presenter.onSubmitButtonClicked(
                edtNik.text.toString(),
                edtName.text.toString(),
                edtNameRelawan.text.toString(),
                edtNameKoordinator.text.toString(),
                edtNameTandeman.text.toString(),
                edtNumberPhone.text.toString(),
                edtNumberTps.text.toString(),
                edtKecamatan.text.toString(),
                edtKabupaten.text.toString()
            )
        }
    }


    private fun setButtonState() {
        // Check if all specified EditTexts are not empty
        val isAllFieldsNotEmpty =
            listOf(
                edtNik,edtName, edtNameRelawan, edtNameKoordinator, edtNameTandeman,
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
            if (isAllFieldsNotEmpty) android.R.color.white
            else android.R.color.holo_green_light

        // Set background drawable and text color based on the conditions
        val backgroundDrawable =
            if (isAllFieldsNotEmpty && isPhoneNumberValid) {
                btnSubmitInput.setTextColor(ContextCompat.getColor(this, textColorResId))
                if (isCheckBoxChecked) {
                    R.drawable.button_background_true
                } else {
                    R.drawable.button_background_false
                }
            } else {
                btnSubmitInput.setTextColor(ContextCompat.getColor(this, textColorResId))
                R.drawable.button_background_false
            }

        btnSubmitInput.setBackgroundResource(backgroundDrawable)
    }


    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToResultRelawan() {
        val intent = Intent(this, ResultRelawanActivity::class.java)
        startActivity(intent)
    }
}







