package com.jonacenter.ajprelawan.data

data class DptData(
    val nik: String,
    val nama: String,
    val nkk: String,
    val jenis_kelamin: String?,
    val alamat: String,
    val kabupaten: String,
    val kecamatan: String,
    val kelurahan: String,
    val provinsi: String?,
    val tps: String
)