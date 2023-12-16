package com.jonacenter.ajprelawan.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonacenter.ajprelawan.data.LocalData

@Database(entities = [LocalData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDataDao(): LocalDataDao
}
