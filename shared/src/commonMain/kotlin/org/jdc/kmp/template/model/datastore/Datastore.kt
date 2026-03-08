package org.jdc.kmp.template.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

object DatastoreUtil {
    fun createDataStore(producePath: () -> String): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        )

    fun createDataStoreFilename(name: String): String = "$name.preferences_pb"
}

class UserDataStore(val datastore: DataStore<Preferences>)
class DeviceDataStore(val datastore: DataStore<Preferences>)