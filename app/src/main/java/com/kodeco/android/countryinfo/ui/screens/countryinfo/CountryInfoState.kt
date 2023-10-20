package com.kodeco.android.countryinfo.ui.screens.countryinfo

import com.kodeco.android.countryinfo.models.Country

sealed class CountryInfoState {
    data class Loading(val uptime: Int) : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}
