package org.jdc.kmp.template.model.datastore.migration

import androidx.datastore.preferences.core.Preferences

object DevicePreferenceMigration2 : PreferenceMigration(1, 2) {
    override suspend fun migrate(currentData: Preferences): Preferences {
        val mutablePreferences = currentData.toMutablePreferences()

        // do preference migrations from version 1 to 2 here

        return mutablePreferences.toPreferences()
    }
}