package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.networking.CountriesApi
import com.kodeco.android.countryinfo.networking.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CountryInfoScreen(onNavigateToCountryError: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var countries by remember { mutableStateOf(listOf<Country>()) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                val response = CountriesApi.getCountries()

                // I couldn't figure out why this was happening but if there was an error getting
                // the countries it would navigate me to the error page. On the error page there
                // is a button to retry that would pop the stack and send you back to this page
                // to try to get the countries again, but when you click retry it would try to load
                // the page again, fail and be redirected back to the error page as expected.
                // The error would show for a short time but then disappear, but if i put a delay
                // so the navigation does not happen as fast everything would work. Not sure how
                // fix this. You could just comment the delay and change the GET annotation on the
                // Countries service API to all123 so that a 404 error is thrown to reproduce the
                // error i was getting
                delay(1000L)
                withContext(Dispatchers.Main) {
                    when (response) {
                        is NetworkResponse.HTTPSuccess -> countries = response.data
                        else -> onNavigateToCountryError()
                    }
                }
            }
        }

        CountryInfoList(countries = countries)
    }
}

// TODO fill out the preview.
@Preview(showBackground = true)
@Composable
fun CountryInfoScreenPreview() { }
