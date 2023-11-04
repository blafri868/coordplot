package com.kodeco.android.countryinfo.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kodeco.android.countryinfo.databases.daos.CountryDao
import com.kodeco.android.countryinfo.models.Country

const val DATABASE_VERSION = 1

@Database(
    entities = [Country::class],
    version = DATABASE_VERSION
)
abstract class CountryInfoDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "CountryInfo"

        fun buildDatabase(context: Context): CountryInfoDatabase {
            return Room.databaseBuilder(context, CountryInfoDatabase::class.java, DATABASE_NAME).build()
        }
    }

    abstract fun countryDao(): CountryDao
}