package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.databases.daos.CountryDao
import com.kodeco.android.countryinfo.datastores.CountryInfoStore
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService

class CountryRepositoryImpl(
    private val countryDao: CountryDao,
    private val service: CountryService,
    private val prefs: CountryInfoStore
) : CountryRepository {
    override val countries
        get() = countryDao.loadCountries()

    override suspend fun fetchCountries() {
        val response = service.getAllCountries()
        val favorites = countryDao.getFavorites().map { country -> country.commonName }

        val countryList = response.map { country ->
            if (favorites.contains(country.commonName)) {
                country.copy(isFavorite = true)
            } else {
                country
            }
        }
        countryDao.deleteAll()
        countryDao.addCountries(countryList)
    }

    override suspend fun getCountry(name: String): Country? = countryDao.getCountryByCommonName(name)

    override suspend fun favorite(country: Country) {
        val isFavorite = !country.isFavorite
        countryDao.updateCountry(country.copy(isFavorite = isFavorite))
    }
}
