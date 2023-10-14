package com.kodeco.android.countryinfo.ui.screens.countryinfo

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CountryInfoState : Parcelable {
    data class Loading(val upTime: Int) : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

class CountryInfoViewModel(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private var upTime = 0
    private val _uiState: MutableStateFlow<CountryInfoState> = MutableStateFlow(CountryInfoState.Loading(upTime))

    val uiState = _uiState.asStateFlow()

    fun fetchCountries() {
        _uiState.update { CountryInfoState.Loading(upTime) }

        viewModelScope.launch {
            countryRepository.fetchCountries()
                .catch {error ->
                    _uiState.update { CountryInfoState.Error(error) }
                }
                .collect {countryList ->
                    _uiState.update { CountryInfoState.Success(countryList) }
                }
        }
    }

    init {
        viewModelScope.launch {
            while (true) {
                delay(1_000L)
                upTime++

                if (uiState.value is CountryInfoState.Loading) {
                    _uiState.update { CountryInfoState.Loading(upTime) }
                }
            }
        }

        fetchCountries()
    }

    class CountryInfoViewModelFactory(
        private val repository: CountryRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = CountryInfoViewModel(repository) as T
    }
}
