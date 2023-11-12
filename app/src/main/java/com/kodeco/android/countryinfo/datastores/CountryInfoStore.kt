package com.kodeco.android.countryinfo.datastores

import kotlinx.coroutines.flow.Flow

interface CountryInfoStore {
    val isLocalStorageEnabled: Flow<Boolean>
    val isFavoritesFeatureEnabled: Flow<Boolean>

    suspend fun toggleLocalStorageEnabled()
    suspend fun toggleFavoritesFeatureEnabled()
}