package com.kodeco.android.countryinfo.ui.screens.countrylist

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.sample.sampleCountries
import com.kodeco.android.countryinfo.sample.sampleCountry
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun CountryListScreen(
    viewModel: CountryListViewModel,
    onCountryRowTap: (countryIndex: Int) -> Unit,
    onAboutTap: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val transition = updateTransition(
        targetState = state,
        label = "UI state transition"
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.country_info_screen_title)) },
                actions = {
                    IconButton(
                        onClick = onAboutTap,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Help,
                            contentDescription = stringResource(id = R.string.about_content_description),
                        )
                    }
                }
            )
        },
    ) { padding ->
        transition.Crossfade(contentKey = { it.javaClass }) {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                when (val countryListState = it) {
                    is CountryListState.Loading -> Loading()
                    is CountryListState.Success -> CountryInfoList(
                        countries = countryListState.countries,
                        onRefreshTap = viewModel::fetchCountries,
                        onCountryRowTap = onCountryRowTap,
                        onCountryFavorite = { countryIndex ->
                            val country = countryListState.countries[countryIndex]
                            viewModel.favorite(country)
                        }
                    )

                    is CountryListState.Error -> Error(
                        userFriendlyMessageText = stringResource(id = R.string.country_info_error),
                        error = countryListState.error,
                        onRetry = viewModel::fetchCountries,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    CountryListScreen(
        viewModel = CountryListViewModel(
            repository = object : CountryRepository {
                override val countries: Flow<List<Country>>
                    get() = MutableStateFlow(sampleCountries).asStateFlow()

                override suspend fun fetchCountries() {}

                override fun getCountry(index: Int): Country = sampleCountry

                override fun favorite(country: Country) {}
            },
        ),
        onCountryRowTap = {},
        onAboutTap = {},
    )
}
