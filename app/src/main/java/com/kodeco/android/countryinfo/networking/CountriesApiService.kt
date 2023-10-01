package com.kodeco.android.countryinfo.networking

import com.kodeco.android.countryinfo.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApiService {
    @GET("all")
    suspend fun getCountries(): Response<List<Country>>
}
