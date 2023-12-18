package com.jonacenter.ajprelawan.viewRelawan.manual

interface ManualContract {
    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun showToast(message: String)
        fun navigateToResultRelawan()
    }

    interface Presenter {
        fun onSubmitButtonClicked(
            nik: String,
            namaRelawan: String,
            nama: String,
            koordinator: String,
            tandeman: String,
            notelp: String,
            noTps: String,
            kecamatan: String,
            kabupaten: String
        )
    }
}
