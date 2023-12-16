package com.jonacenter.ajprelawan.api

import com.jonacenter.ajprelawan.data.DptResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("democekdpt/api/pemilih")
    suspend fun fetchData(@Query("nik") nik: String): DptResponse
}

