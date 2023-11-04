package com.kodeco.android.countryinfo.ui.screens.countrylist

import com.kodeco.android.countryinfo.models.Country

sealed class CountryListState {
    data object Loading : CountryListState()
    data class FinishedLoading(val message: String) : CountryListState()
}
