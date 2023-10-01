package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName

// TODO fill out CountryInfoRow
@Composable
fun CountryInfoRow(country: Country) {
    val name = country.commonName
    val capital = country.capital?.get(0) ?: ""

    Card(
        modifier = Modifier.fillMaxWidth().padding(all = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(all = 5.dp)
        ) {
            Text(text = "Name: $name")
            Text(text = "Capital: $capital")
        }
    }
}

// TODO fill out the preview.
@Preview(showBackground = true)
@Composable
fun CountryInfoRowPreview() {
    val country = Country(
        name = CountryName("Trinidad & Tobago"),
        capital = listOf("Port of Spain"),
        population = 1L,
        area = 1.0,
        flags = CountryFlags("hhh")
    )
    CountryInfoRow(country)
}
