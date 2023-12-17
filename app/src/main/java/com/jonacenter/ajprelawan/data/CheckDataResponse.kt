package com.jonacenter.ajprelawan.data

data class CheckDataResponse(
    val status: Boolean,
    val responseCode: String,
    val responseMessage: String,
    val data: DptDataResponse?
)




