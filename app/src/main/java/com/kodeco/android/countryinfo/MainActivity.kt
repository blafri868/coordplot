package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "countryInfoScreen") {
                    composable("countryInfoScreen") {
                        CountryInfoScreen(
                            onNavigateToCountryError = {
                                navController.navigate("countryError")
                            }
                        )
                    }
                    composable("countryError") {
                        CountryErrorScreen(
                            onRetry = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
