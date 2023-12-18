package com.jonacenter.ajprelawan.api

import com.jonacenter.ajprelawan.api.Config.addData
import com.jonacenter.ajprelawan.api.Config.cekDataDpt
import com.jonacenter.ajprelawan.api.Config.getData
import com.jonacenter.ajprelawan.data.AddDataRequest
import com.jonacenter.ajprelawan.data.ApiResponse
import com.jonacenter.ajprelawan.data.CheckDataResponse
import com.jonacenter.ajprelawan.data.RelawanData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(cekDataDpt)
    suspend fun checkDataDpt(@Query("nik") nik: String): Response<CheckDataResponse>

    @POST(addData)
    fun postAddData(@Body requestData: AddDataRequest): Call<ApiResponse>

    @GET(getData)
    fun getResultRelawanData(): Call<List<RelawanData>>
}

