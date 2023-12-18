package com.jonacenter.ajprelawan.api

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.jonacenter.ajprelawan.MyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BaseDataInstance {

    fun getBaseInstance(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            // Uncomment the line below to add ChuckerInterceptor
             .addInterceptor(ChuckerInterceptor(MyApplication.getContext()!!))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Config.URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
