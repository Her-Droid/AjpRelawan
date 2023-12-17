package com.jonacenter.ajprelawan.data

data class AddDataRequest(
    val nik: String,
    val nama_relawan: String,
    val koordinator: String,
    val tandeman: String,
    val notelp: String,
    val no_tps: String,
    val kecamatan: String,
    val kabupaten: String
)

