package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.models.Country

@Composable
fun CountryInfoList(
    countries: List<Country>,
    onRefreshTap: () -> Unit,
    onCountryRowTap: (countryName: String) -> Unit,
    isFavoritesFeatureEnabled: Boolean,
    onCountryRowFavorite: (country: Country) -> Unit,
    isFetchCountriesSuccessful: Boolean,
    isLocalStorageEnabled: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = onRefreshTap) {
                Text(text = stringResource(id = R.string.country_info_refresh_button_text))
            }
        }
        if (isFetchCountriesSuccessful || isLocalStorageEnabled) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(countries) { country ->
                    CountryInfoRow(
                        country = country,
                        onTap = {
                            onCountryRowTap(country.commonName)
                        },
                        isFavoritesFeatureEnabled = isFavoritesFeatureEnabled,
                        onFavorite = {
                            onCountryRowFavorite(country)
                        }
                    )
                }
            }
        }
    }
}
