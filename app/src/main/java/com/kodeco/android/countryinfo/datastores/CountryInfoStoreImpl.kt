package com.kodeco.android.countryinfo.datastores

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class CountryInfoStoreImpl(private val context: Context) : CountryInfoStore {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "country_prefs")
        private val LOCAL_STORAGE_ENABLED_KEY = booleanPreferencesKey("local_storage_enabled")
        private val FAVORITES_FEATURE_ENABLED_KEY = booleanPreferencesKey("favorites_feature_enabled")
    }

    private val dataStore = context.dataStore

    private val storeFlow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }

    override val isLocalStorageEnabled
        get() = storeFlow.map { it[LOCAL_STORAGE_ENABLED_KEY] ?: false }

    override val isFavoritesFeatureEnabled
        get() = storeFlow.map { it[FAVORITES_FEATURE_ENABLED_KEY] ?: false }

    override suspend fun toggleLocalStorageEnabled() {
        dataStore.edit { prefs ->
            val isLocalStorageEnabled = prefs[LOCAL_STORAGE_ENABLED_KEY] ?: false
            prefs[LOCAL_STORAGE_ENABLED_KEY] = !isLocalStorageEnabled
        }
    }

    override suspend fun toggleFavoritesFeatureEnabled() {
        dataStore.edit { prefs ->
            val isFavoritesFeatureEnabled = prefs[FAVORITES_FEATURE_ENABLED_KEY] ?: false
            prefs[FAVORITES_FEATURE_ENABLED_KEY] = !isFavoritesFeatureEnabled
        }
    }
}