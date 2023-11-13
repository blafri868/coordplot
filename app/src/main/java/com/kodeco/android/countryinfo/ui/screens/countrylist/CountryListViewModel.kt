package com.kodeco.android.countryinfo.ui.screens.countrylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.datastores.CountryInfoStore
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val repository: CountryRepository,
    private val prefs: CountryInfoStore
) : ViewModel() {
    val countriesFlow = repository.countries

    private val _uiState = MutableStateFlow<CountryListState>(CountryListState.Loading)
    val uiState: StateFlow<CountryListState> = _uiState.asStateFlow()

    private val _isFavoritesFeatureEnabled = MutableStateFlow<Boolean>(false)
    val isFavoritesFeatureEnabled = _isFavoritesFeatureEnabled.asStateFlow()

    private val _isLocalStorageEnabled = MutableStateFlow<Boolean>(false)
    val isLocalStorageEnabled = _isLocalStorageEnabled.asStateFlow()

    private val _fetchCountriesSuccessful = MutableStateFlow<Boolean>(false)
    val fetchCountriesSuccessful = _fetchCountriesSuccessful.asStateFlow()

    init {
        fetchCountries()
        viewModelScope.launch {
            prefs.isFavoritesFeatureEnabled.collect {
                _isFavoritesFeatureEnabled.value = it
            }
        }
        viewModelScope.launch {
            prefs.isLocalStorageEnabled.collect() {
                _isLocalStorageEnabled.value = it
            }
        }
    }

    class CountryInfoViewModelFactory(
        private val repository: CountryRepository,
        private val prefs: CountryInfoStore
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CountryListViewModel(repository, prefs) as T
    }

    fun fetchCountries() {
        _uiState.value = CountryListState.Loading

        viewModelScope.launch {
            val message = try {
                repository.fetchCountries()
                _fetchCountriesSuccessful.value = true
                "Successfully updated the list of countries"
            } catch (e: Exception) {
                _fetchCountriesSuccessful.value = false
                delay(1000L)
                "Failed to updated the list of countries"
            }

            _uiState.value = CountryListState.FinishedLoading(message)
        }
    }

    fun favorite(country: Country) {
        viewModelScope.launch {
            repository.favorite(country)
        }
    }
}
