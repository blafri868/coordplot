package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.sample.sampleCountries
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel,
) {
    val state = viewModel.uiState.collectAsState()

    Surface {
        when(val countryInfoState = state.value) {
            is CountryInfoState.Loading -> Loading(countryInfoState.uptime)
            is CountryInfoState.Success -> CountryInfoList(
                countries = countryInfoState.countries,
            ) {
                viewModel.fetchCountries()
            }
            is CountryInfoState.Error -> Error(countryInfoState.error) {
                viewModel.fetchCountries()
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    CountryInfoScreen(
        viewModel = CountryInfoViewModel(
            repository = object : CountryRepository {
                override fun fetchCountries(): Flow<List<Country>> = flow {
                    emit(sampleCountries)
                }

                override fun getCountry(index: Int): Country? {
                    return sampleCountries.getOrNull(index)
                }
            },
        ),
    )
}
