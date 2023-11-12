package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.datastores.CountryInfoStore
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateUp: () -> Unit
) {
    val isLocalStorageEnabled by viewModel.isLocalStorageEnabled.collectAsState()
    val isFavoritesFeatureEnabled by viewModel.isFavoritesFeatureEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings_screen_title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.nav_back_content_description),
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.settings_local_storage_feature),
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    checked = isLocalStorageEnabled,
                    onCheckedChange = { viewModel.toggleLocalStorageEnabled() }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.settings_favorites_feature),
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    checked = isFavoritesFeatureEnabled,
                    onCheckedChange = { viewModel.toggleFavoritesFeatureEnabled() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    MyApplicationTheme {
        SettingsScreen(
            viewModel = SettingsViewModel(
                prefs = object : CountryInfoStore {
                    override val isLocalStorageEnabled: Flow<Boolean>
                        get() = flow {}
                    override val isFavoritesFeatureEnabled: Flow<Boolean>
                        get() = flow {}

                    override suspend fun toggleLocalStorageEnabled() {}

                    override suspend fun toggleFavoritesFeatureEnabled() {}

                }
            ),
            onNavigateUp = {}
        )
    }
}