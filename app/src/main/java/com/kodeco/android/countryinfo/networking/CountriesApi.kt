package com.kodeco.android.countryinfo.networking

import com.kodeco.android.countryinfo.model.Country
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CountriesApi {
    private const val BASE_URL = "https://restcountries.com/v3.1/"

    private val apiService: CountriesApiService by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
            .create(CountriesApiService::class.java)
    }

    suspend fun getCountries(): NetworkResponse<List<Country>> {
        return try {
            val response = apiService.getCountries()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                NetworkResponse.HTTPSuccess(body)
            } else {
                NetworkResponse.HTTPError(response.code())
            }
        } catch (error: Throwable) {
            NetworkResponse.Exception(error)
        }
    }
}