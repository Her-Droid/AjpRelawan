package com.jonacenter.ajprelawan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalData(
    @PrimaryKey
    val nik: String,
    val nama: String,
    val tps: String,
    val kecamatan: String,
    val kabupaten: String,
    val namaRelawan: String,
    val namaTandeman: String,
    val namaKoordinator: String,
    val numberPhone: String
)
