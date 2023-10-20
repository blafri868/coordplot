package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CountryDetailsViewModel(
    private val countryId: Int,
    private val repository: CountryRepository
) : ViewModel() {
    private val _country = MutableStateFlow<Country?>(null)
    val country = _country.asStateFlow()

    init {
        _country.update { repository.getCountry(countryId) }
    }

    class CountryDetailsViewModelFactory(
        private val countryId: Int,
        private val repository: CountryRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CountryDetailsViewModel(countryId, repository) as T
    }
}
