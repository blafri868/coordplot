package com.kodeco.android.countryinfo.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.datastores.CountryInfoStore
import com.kodeco.android.countryinfo.datastores.CountryInfoStoreImpl
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.ui.screens.Screen
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListScreen
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListViewModel
import com.kodeco.android.countryinfo.ui.screens.settings.SettingsScreen
import com.kodeco.android.countryinfo.ui.screens.settings.SettingsViewModel

@Composable
fun CountryInfoNavHost(
    repository: CountryRepository,
    prefs: CountryInfoStore
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.path) {
        composable(Screen.List.path) {
            CountryListScreen(
                viewModel = viewModel(
                    factory = CountryListViewModel.CountryInfoViewModelFactory(
                        repository = repository,
                        prefs = prefs
                    ),
                ),
                onCountryRowTap = { countryName ->
                    navController.navigate("${Screen.Details.path}/$countryName")
                },
                onAboutTap = { navController.navigate(Screen.About.path) },
                onSettingsTap = { navController.navigate(Screen.Settings.path) }
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

        composable(Screen.Settings.path) {
            SettingsScreen(
                viewModel = viewModel(
                    factory = SettingsViewModel.SettingsViewModelFactory(
                        prefs = prefs
                    ),
                ),
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}
