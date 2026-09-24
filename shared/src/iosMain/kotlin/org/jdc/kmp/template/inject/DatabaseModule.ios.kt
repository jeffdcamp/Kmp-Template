package org.jdc.kmp.template.inject

import androidx.room3.Room
import androidx.room3.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.kmp.template.model.datastore.DeviceDataStore
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserDataStore
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val databaseBuilderModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        Room.databaseBuilder<MainDatabase>(
            name = documentDirectoryPath() + "/${MainDatabase.DATABASE_NAME}",
        )
    }
}

actual val datastoreModule = module {
    single<UserDataStore> {
        UserDataStore(
            datastore = UserPreferenceDataSource.createDataStore {
                documentDirectoryPath() + "/${createDataStoreFilename(UserPreferenceDataSource.NAME)}"
            }
        )
    }

    single<DeviceDataStore> {
        DeviceDataStore(
            datastore = DevicePreferenceDataSource.createDataStore {
                documentDirectoryPath() + "/${createDataStoreFilename(DevicePreferenceDataSource.NAME)}"
            }
        )
    }
}

actual val coroutineModule = module {
    single<AppCoroutineDispatchers> {
        AppCoroutineDispatchers(
            default = Dispatchers.Default,
            io = Dispatchers.IO,
            main = Dispatchers.Main,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectoryPath(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null,
    )
    return requireNotNull(documentDirectory?.path) { "Unable to locate iOS document directory" }
}
