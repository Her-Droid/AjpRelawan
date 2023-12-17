package com.jonacenter.ajprelawan.viewRelawan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.api.BaseDataInstance
import com.jonacenter.ajprelawan.data.RelawanData
import kotlinx.coroutines.launch

class RelawanViewModel : ViewModel() {

    private val apiService = BaseDataInstance.getBaseInstance().create(ApiService::class.java)

    private val _relawanData = MutableLiveData<List<RelawanData>>()
    val relawanData: LiveData<List<RelawanData>> get() = _relawanData

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> get() = _totalCount

    fun fetchData(nik: Long? = null) {
        viewModelScope.launch {
            try {
                val data = apiService.getData(nik)
                _relawanData.value = data
                _totalCount.value = data.size
            } catch (e: Exception) {
                // Handle the exception (e.g., show an error message)
                e.printStackTrace()
            }
        }
    }
}
