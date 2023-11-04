package com.kodeco.android.countryinfo.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.ui.screens.Screen
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListScreen
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListViewModel

@Composable
fun CountryInfoNavHost(
    repository: CountryRepository,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.path) {
        composable(Screen.List.path) {
            CountryListScreen(
                viewModel = viewModel(
                    factory = CountryListViewModel.CountryInfoViewModelFactory(
                        repository = repository,
                    ),
                ),
                onCountryRowTap = { countryName ->
                    navController.navigate("${Screen.Details.path}/$countryName")
                },
                onAboutTap = { navController.navigate(Screen.About.path) },
            )
        }

        composable(
            route = "${Screen.Details.path}/{countryName}",
            arguments = listOf(navArgument("countryName") { type = NavType.StringType }),
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments!!.getString("countryName") ?: ""
            CountryDetailsScreen(
                countryName = countryName,
                viewModel = viewModel(
                    factory = CountryDetailsViewModel.CountryDetailsViewModelFactory(
                        repository = repository,
                    ),
                ),
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(Screen.About.path) {
            AboutScreen(
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}
