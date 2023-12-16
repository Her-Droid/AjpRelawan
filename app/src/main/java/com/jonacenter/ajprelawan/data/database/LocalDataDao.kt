package com.jonacenter.ajprelawan.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jonacenter.ajprelawan.data.LocalData

@Dao
interface LocalDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: LocalData)

    @Query("SELECT * FROM localdata")
    suspend fun getAllData(): List<LocalData>
}
