package com.jonacenter.ajprelawan.data

data class ApiResponse(
    val status: Boolean,
    val responseCode: String,
    val responseMessage: String,
    val data: Any?
)