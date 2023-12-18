package com.jonacenter.ajprelawan.data

data class RelawanData(
    val id: Int,
    val nik: Int,
    val nama: String,
    val nama_relawan: String,
    val koordinator: String,
    val tandeman: String,
    val notelp: Int,
    val no_tps: Int,
    val kecamatan: String,
    val kabupaten: String
)
