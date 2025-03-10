package com.jonacenter.ajprelawan.data

import com.google.gson.annotations.SerializedName

data class AddDataResponse(

	@field:SerializedName("koordinator")
	val koordinator: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("notelp")
	val notelp: Int? = null,

	@field:SerializedName("tandeman")
	val tandeman: String? = null,

	@field:SerializedName("no_tps")
	val noTps: String? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: Int? = null,

	@field:SerializedName("nama_relawan")
	val namaRelawan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kabupaten")
	val kabupaten: Int? = null
)
