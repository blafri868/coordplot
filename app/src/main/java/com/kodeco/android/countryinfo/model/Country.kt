package com.kodeco.android.countryinfo.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(val name: CountryName,
                   val capital: List<String>?,
                   val population: Long,
                   val area: Double,
                   val flags: CountryFlags) {
    val commonName: String
        get() = name.common
}