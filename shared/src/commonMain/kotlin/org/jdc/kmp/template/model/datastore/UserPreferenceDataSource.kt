package org.jdc.kmp.template.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import okio.Path.Companion.toPath
import org.jdc.kmp.template.model.datastore.migration.PreferenceMigrations

class UserPreferenceDataSource(
    userDataStore: UserDataStore
) {
    private val dataStore: DataStore<Preferences> = userDataStore.datastore

    val directorySortByLastNamePref: DatastorePrefItem<Boolean> = DatastorePrefItem.create(dataStore, Keys.DIRECTORY_SORT, true)

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    private object Keys {
        val DIRECTORY_SORT = booleanPreferencesKey("directorySort")
    }

    companion object {
        const val NAME = "user"
        private const val VERSION = 1

        fun createDataStore(producePath: () -> String): DataStore<Preferences> {
            return PreferenceDataStoreFactory.createWithPath(
                migrations = listOf(
                    PreferenceMigrations(VERSION, emptyList())

                ),
                produceFile = { producePath().toPath() }
            )
        }
    }
}