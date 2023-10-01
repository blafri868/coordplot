package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName

// TODO fill out CountryInfoList
@Composable
fun CountryInfoList(countries: List<Country>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 5.dp)) {
        items(items = countries) {country ->
            CountryInfoRow(country = country)
        }
    }
}

// TODO fill out the preview.
@Preview(showBackground = true)
@Composable
fun CountryInfoListPreview() {
    val countries = listOf(
        Country(
            name = CountryName("Trinidad & Tobago"),
            capital = listOf("Port of Spain"),
            population = 1L,
            area = 1.0,
            flags = CountryFlags("hhh")
        ),
        Country(
            name = CountryName("Barbados"),
            capital = listOf("Bridge Town"),
            population = 1L,
            area = 1.0,
            flags = CountryFlags("hhh")
        ),
        Country(
            name = CountryName("Jamaica"),
            capital = listOf("Kingston"),
            population = 1L,
            area = 1.0,
            flags = CountryFlags("hhh")
        )
    )

    CountryInfoList(countries)
}
