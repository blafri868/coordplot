package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.flow.Flows
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.sample.sampleCountries

@Composable
fun CountryInfoList(
    countries: List<Country>,
    onRefreshClick: () -> Unit,
) {
    val tapCount: Int by Flows.tapFlow.collectAsState()
    val backCount: Int by Flows.backFlow.collectAsState()
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    Column {
        TopBar(tapCount, backCount, onRefreshClick)
        selectedCountry?.let { country ->
            CountryDetailsScreen(country) {
                selectedCountry = null
                Flows.tapBack()
            }
        } ?: run {
            LazyColumn {
                items(countries) { country ->
                    CountryInfoRow(country) {
                        selectedCountry = country
                        Flows.tap()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryInfoListPreview() {
    CountryInfoList(
        countries = sampleCountries,
    ) {}
}
