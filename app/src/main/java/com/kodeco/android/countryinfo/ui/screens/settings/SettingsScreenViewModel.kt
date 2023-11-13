package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.datastores.CountryInfoStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val prefs: CountryInfoStore
) : ViewModel() {
    private val _isLocalStorageEnabled = MutableStateFlow<Boolean>(false)
    val isLocalStorageEnabled = _isLocalStorageEnabled.asStateFlow()

    private val _isFavoritesFeatureEnabled = MutableStateFlow<Boolean>(false)
    val isFavoritesFeatureEnabled = _isFavoritesFeatureEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.isLocalStorageEnabled.collect {
                _isLocalStorageEnabled.value = it
            }
        }

        viewModelScope.launch {
            prefs.isFavoritesFeatureEnabled.collect {
                _isFavoritesFeatureEnabled.value = it
            }
        }
    }

    fun toggleLocalStorageEnabled() {
        viewModelScope.launch {
            prefs.toggleLocalStorageEnabled()
        }
    }

    fun toggleFavoritesFeatureEnabled() {
        viewModelScope.launch {
            prefs.toggleFavoritesFeatureEnabled()
        }
    }

    class SettingsViewModelFactory(
        private val prefs: CountryInfoStore
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SettingsViewModel(prefs) as T
    }
}