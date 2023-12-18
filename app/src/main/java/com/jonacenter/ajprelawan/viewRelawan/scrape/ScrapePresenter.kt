package com.jonacenter.ajprelawan.viewRelawan.scrape

import android.content.Context
import android.widget.Toast
import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.ApiResponse
import com.jonacenter.ajprelawan.data.CheckDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScrapePresenter(private val view: ScrapeContract.View, private val apiService: ApiService, private val context: Context) :
    ScrapeContract.Presenter {

    override fun checkData(nik: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val checkDataResponse = apiService.checkDataDpt(nik)
                handleCheckDataResponse(checkDataResponse.body())
            } catch (e: Exception) {
                e.printStackTrace()
                view.showCheckDataFailed("Error checking data")
            }
        }
    }

    private fun handleCheckDataResponse(response: CheckDataResponse?) {
        when (response) {
            null -> {
                view.showCheckDataFailed("Unable to fetch data")
            }
            else -> {
                if (response.status && response.responseCode == "00") {
                    // Show a Toast message with the response message on success
                    showToast(response.responseMessage)
                    view.showCheckDataSuccess(response.data!!)
                } else {
                    view.showCheckDataFailed(response.responseMessage)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun addData(addDataRequest: AddDataRequest) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val addDataResponse = apiService.postAddData(addDataRequest).execute()
                handleAddDataResponse(addDataResponse.body())
            } catch (e: Exception) {
                e.printStackTrace()
                view.showAddDataFailed("Error adding data")
            }
        }
    }

    private fun handleAddDataResponse(response: ApiResponse?) {
        when {
            response == null -> {
                view.showAddDataFailed("Null response")
            }
            response.status -> {
                view.showAddDataSuccess()
            }
            else -> {
                view.showAddDataFailed(response.responseMessage)
            }
        }
    }
}

