package com.jonacenter.ajprelawan.data.repository

import com.jonacenter.ajprelawan.api.ApiService
import com.jonacenter.ajprelawan.data.DptResponse
import com.jonacenter.ajprelawan.data.LocalData
import com.jonacenter.ajprelawan.data.database.LocalDataDao

class Repository(private val apiService: ApiService, private val localDataDao: LocalDataDao) {
    suspend fun fetchDataFromApi(nik: String): DptResponse {
        return apiService.fetchData(nik)
    }

    suspend fun saveDataToLocal(data: LocalData) {
        localDataDao.insertData(data)
    }

    suspend fun getAllLocalData(): List<LocalData> {
        return localDataDao.getAllData()
    }
}
