package com.jonacenter.ajprelawan.viewRelawan.resultRelawan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jonacenter.ajprelawan.R
import com.jonacenter.ajprelawan.data.RelawanData

class ResultRelawanAdapter(private val relawanList: List<RelawanData>) :
    RecyclerView.Adapter<ResultRelawanAdapter.ResultRelawanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultRelawanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result_relawan, parent, false)
        return ResultRelawanViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultRelawanViewHolder, position: Int) {
        val relawanData = relawanList[position]
        holder.bind(relawanData)
    }

    override fun getItemCount(): Int {
        return relawanList.size
    }

    inner class ResultRelawanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNumberTps: TextView = itemView.findViewById(R.id.tvNumberTps)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvIdCard: TextView = itemView.findViewById(R.id.tvIdCard)
        private val tvNumberPhone: TextView = itemView.findViewById(R.id.tvNumberPhone)
        private val tvKabupatenKota: TextView = itemView.findViewById(R.id.tvKabupatenKota)
        private val tvKecamatan: TextView = itemView.findViewById(R.id.tvKecamatan)
        private val tvNameRelawan: TextView = itemView.findViewById(R.id.tvNameRelawan)
        private val tvNameTandeman: TextView = itemView.findViewById(R.id.tvNameTandeman)
        private val tvNameKoordinator: TextView = itemView.findViewById(R.id.tvNameKoordinator)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            // Set an initial state when the ViewHolder is created
            checkBox.isChecked = true
            checkBox.isEnabled = false
        }

        fun bind(relawanData: RelawanData) {
            tvNumberTps.text = "No Tps: ${relawanData.no_tps}"
//            tvNumberTps.text = relawanData.no_tps.toString()
            tvName.text = relawanData.nama
            tvIdCard.text = relawanData.nik.toString()
            tvNumberPhone.text = relawanData.notelp.toString()
            tvKabupatenKota.text = relawanData.kabupaten
            tvKecamatan.text = relawanData.kecamatan
            tvNameRelawan.text = relawanData.nama_relawan
            tvNameTandeman.text = relawanData.tandeman
            tvNameKoordinator.text = relawanData.koordinator

            // Set a click listener for the CheckBox
            checkBox.setOnClickListener {
                // Update the state based on CheckBox click
                checkBox.isChecked = !checkBox.isChecked
                // You can add more logic here if needed

                // If checked, disable the CheckBox; if unchecked, enable it
                checkBox.isEnabled = !checkBox.isChecked
            }
        }
    }

}
