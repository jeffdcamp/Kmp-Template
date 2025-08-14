package org.jdc.kmp.template.inject

import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import org.jdc.kmp.template.model.datastore.DatastoreUtil
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.kmp.template.model.datastore.DeviceDataStore
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserDataStore
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.dsl.module
import java.io.File

actual val databaseModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), MainDatabase.DATABASE_NAME)
        Logger.i { "DB File: ${dbFile.absolutePath}" }
        Room.databaseBuilder<MainDatabase>(
            name = dbFile.absolutePath,
        )
    }
}

actual val datastoreModule = module {
    single<UserDataStore> {
        UserDataStore(
            UserPreferenceDataSource.createDataStore {
                createDataStoreFilename(DatastoreUtil.USER)
            }
        )
    }

    single<DeviceDataStore> {
        DeviceDataStore(
            DevicePreferenceDataSource.createDataStore {
                createDataStoreFilename(DatastoreUtil.DEVICE)
            }
        )
    }
}

actual val coroutineModule = module {
    single<AppCoroutineDispatchers> {
        AppCoroutineDispatchers(
            default = Dispatchers.Default,
            io = Dispatchers.Default,
            main = Dispatchers.Main,
        )
    }
}
