package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(private val service: CountryService) : CountryRepository {
    private var countries: List<Country> = emptyList()

    override fun fetchCountries(): Flow<List<Country>> = flow {
        delay(2_000L)

        val countriesResponse = service.getAllCountries()

        if (countriesResponse.isSuccessful) {
            countries = countriesResponse.body()!!
            emit(countries)
        } else {
            throw Throwable("Request failed: ${countriesResponse.message()}")
        }
    }

    override fun getCountry(code: String): Country? {
        return countries.find { country ->
            country.cca3 == code
        }
    }
}