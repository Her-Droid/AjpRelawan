package com.jonacenter.ajprelawan.api

import com.jonacenter.ajprelawan.api.Config.addData
import com.jonacenter.ajprelawan.api.Config.cekDataDpt
import com.jonacenter.ajprelawan.api.Config.getData
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.AddDataResponse
import com.jonacenter.ajprelawan.data.DptResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(cekDataDpt)
    suspend fun fetchData(@Query("nik") nik: String): DptResponse

    @POST("addData")
    fun postAddData(@Body requestData: AddDataRequest): Call<ResponseBody>

    @POST(getData)
    suspend fun getData()
}

