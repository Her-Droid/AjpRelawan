package com.jonacenter.ajprelawan.viewRelawan.scrape

import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.ApiResponse
import com.jonacenter.ajprelawan.data.CheckDataResponse
import com.jonacenter.ajprelawan.data.DptDataResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScrapeContract {
    interface View {
        fun showCheckDataSuccess(data: DptDataResponse)
        fun showCheckDataFailed(message: String)
        fun showAddDataSuccess()
        fun showAddDataFailed(message: String)
    }

    interface Presenter {
        fun checkData(nik: String)
        fun addData(addDataRequest: AddDataRequest)
    }
}


