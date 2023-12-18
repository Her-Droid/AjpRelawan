package com.jonacenter.ajprelawan.data

data class RelawanData(
    val id: Int,
    val nik: Long,
    val nama: String,
    val nama_relawan: String,
    val koordinator: String,
    val tandeman: String,
    val notelp: Long,
    val no_tps: Int,
    val kecamatan: String,
    val kabupaten: String
)
