package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(
    private val service: CountryService,
) : CountryRepository {

    private var countries = mutableListOf<Country>()

    override fun fetchCountries(): Flow<List<Country>> = flow {
        delay(1_000) // Added for displaying the uptime longer on the loading screen.
        val countriesResponse = service.getAllCountries()

        countries.clear()
        countries.addAll(
            if (countriesResponse.isSuccessful) {
                countriesResponse.body()!!
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }
        )

        emit(countries)
    }

    override fun getCountry(index: Int): Country? {
        return countries.getOrNull(index)
    }
}
