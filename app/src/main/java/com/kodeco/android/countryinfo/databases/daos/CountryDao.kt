package com.kodeco.android.countryinfo.databases.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kodeco.android.countryinfo.models.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun loadCountries(): Flow<List<Country>>

    @Query("SELECT * FROM countries where commonName = :commonName")
    suspend fun getCountryByCommonName(commonName: String): Country

    @Query("SELECT * FROM countries where isFavorite = 1")
    suspend fun getFavorites(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(countries: List<Country>)

    @Query("DELETE FROM countries")
    suspend fun deleteAll()

    @Update
    suspend fun updateCountry(country: Country)
}