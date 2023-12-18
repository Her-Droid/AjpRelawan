package com.jonacenter.ajprelawan.viewRelawan.resultRelawan

import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.api.BaseDataInstance
import com.jonacenter.ajprelawan.data.RelawanData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultRelawanPresenter(private val view: ResultRelawanContract.View) : ResultRelawanContract.Presenter {

    private val apiService = BaseDataInstance.getBaseInstance().create(ApiService::class.java)

    override fun fetchData() {
        // Make the API call using Retrofit
        try {
            val call = apiService.getResultRelawanData()
            call.enqueue(object : Callback<List<RelawanData>> {
                override fun onResponse(call: Call<List<RelawanData>>, response: Response<List<RelawanData>>) {
                    if (response.isSuccessful) {
                        val data = response.body() ?: emptyList()
                        view.showRelawanData(data)
                        view.showTotalCount(data.size)
                    } else {
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<List<RelawanData>>, t: Throwable) {
                    // Handle failure
                }
            })
        } catch (e: Exception) {
            // Handle exception if the request fails
        }
    }

    override fun searchByNik(nik: Long?) {
        // Make the API call using Retrofit for searching by nik
        // Implement this based on your API
    }
}
