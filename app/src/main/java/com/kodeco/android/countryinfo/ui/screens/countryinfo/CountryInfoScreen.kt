package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import retrofit2.Retrofit

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel = viewModel()
) {
    val state = viewModel.uiState.collectAsState()

    Surface {
        when(val curState = state.value) {
            is CountryInfoState.Loading -> Loading(curState.upTime)
            is CountryInfoState.Success -> CountryInfoList(curState.countries) {
                viewModel.fetchCountries()
            }
            is CountryInfoState.Error -> Error(curState.error) {
                viewModel.fetchCountries()
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .build()

    val service: CountryService = retrofit.create(CountryService::class.java)

    CountryInfoScreen(viewModel = viewModel(
        factory = CountryInfoViewModel.CountryInfoViewModelFactory(CountryRepositoryImpl(service))
    ))
}
