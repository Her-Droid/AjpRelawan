package com.jonacenter.ajprelawan.viewRelawan.resultRelawan

import com.jonacenter.ajprelawan.data.RelawanData

interface ResultRelawanContract {
    interface View {
        fun showTotalCount(totalCount: Int)
        fun showRelawanData(data: List<RelawanData>)
    }

    interface Presenter {
        fun fetchData()
        fun searchByNik(nik: Long?)
    }
}
