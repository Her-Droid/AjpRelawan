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
    @GET(Config.cekDataDpt)
    suspend fun checkDataDpt(@Query("nik") nik: String): Response<CheckDataResponse>

    @POST(Config.addData)
    suspend fun addData(@Body requestData: AddDataRequest): Response<ApiResponse>


    @POST(addData)
    fun postAddData(@Body requestData: AddDataRequest): Call<ResponseBody>

    @GET("getData")
    suspend fun getData(@Query("nik") nik: Long?): List<RelawanData>


}

