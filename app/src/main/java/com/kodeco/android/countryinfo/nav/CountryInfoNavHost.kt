package com.kodeco.android.countryinfo.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel

@Composable
fun CountryInfoNavHost(
    repository: CountryRepository,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "countries") {
        composable("countries") {
            CountryInfoScreen(
                viewModel = viewModel(factory = CountryInfoViewModel.CountryInfoViewModelFactory(repository)),
                onCountryRowTap = { countryIndex ->
                    navController.navigate("countries/$countryIndex")
                }
            )
        }

        composable(
            route = "countries/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )
        ) {
            CountryDetailsScreen(
                viewModel = viewModel(
                    factory = CountryDetailsViewModel.CountryDetailsViewModelFactory(
                        countryId = it.arguments!!.getInt("index"),
                        repository = repository
                    )
                ),
                onNavigateUp = { navController.popBackStack() }
            )
        }
    }
}
