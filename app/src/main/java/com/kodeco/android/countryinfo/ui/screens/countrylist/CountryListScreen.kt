package com.kodeco.android.countryinfo.ui.screens.countrylist

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.datastores.CountryInfoStore
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.sample.sampleCountries
import com.kodeco.android.countryinfo.sample.sampleCountry
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun CountryListScreen(
    viewModel: CountryListViewModel,
    onCountryRowTap: (countryName: String) -> Unit,
    onAboutTap: () -> Unit,
    onSettingsTap: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val countries by viewModel.countriesFlow.collectAsState(initial = emptyList())
    val isFavoritesFeatureEnabled by viewModel.isFavoritesFeatureEnabled.collectAsState()
    val isLocalStorageEnabled by viewModel.isLocalStorageEnabled.collectAsState()
    val isFetchCountriesSuccessful by viewModel.fetchCountriesSuccessful.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.country_info_screen_title)) },
                actions = {
                    IconButton(
                        onClick = onSettingsTap,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Comment,
                            contentDescription = stringResource(id = R.string.settings_page_button),
                        )
                    }
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
        val transition = updateTransition(
            targetState = state,
            label = "list_state_transition",
        )
        transition.Crossfade(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentKey = { it.javaClass },
        ) { state ->
            when (state) {
                is CountryListState.Loading -> Loading()
                is CountryListState.FinishedLoading -> {
                    CountryInfoList(
                        countries = countries,
                        onRefreshTap = viewModel::fetchCountries,
                        onCountryRowTap = onCountryRowTap,
                        isFavoritesFeatureEnabled = isFavoritesFeatureEnabled,
                        onCountryRowFavorite = viewModel::favorite,
                        isFetchCountriesSuccessful = isFetchCountriesSuccessful,
                        isLocalStorageEnabled = isLocalStorageEnabled
                    )
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar(
                            message = state.message,
                            duration = SnackbarDuration.Short
                        )
                    }
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

                override suspend fun getCountry(name: String): Country = sampleCountry

                override suspend fun favorite(country: Country) {}
            },
            prefs = object : CountryInfoStore {
                override val isLocalStorageEnabled: Flow<Boolean>
                    get() = flow {}
                override val isFavoritesFeatureEnabled: Flow<Boolean>
                    get() = flow {}

                override suspend fun toggleLocalStorageEnabled() {}

                override suspend fun toggleFavoritesFeatureEnabled() {}

            }
        ),
        onCountryRowTap = {},
        onAboutTap = {},
        onSettingsTap = {}
    )
}
