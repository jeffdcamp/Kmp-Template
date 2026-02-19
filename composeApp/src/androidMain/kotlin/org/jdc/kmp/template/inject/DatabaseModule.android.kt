package org.jdc.kmp.template.inject

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.kmp.template.model.datastore.DeviceDataStore
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserDataStore
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val databaseBuilderModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath(MainDatabase.DATABASE_NAME)
        Room.databaseBuilder<MainDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}

actual val datastoreModule = module {
    single<UserDataStore> {
        UserDataStore(
            datastore = UserPreferenceDataSource.createDataStore {
                androidContext().filesDir.resolve(createDataStoreFilename(UserPreferenceDataSource.NAME)).absolutePath
            }
        )
    }

    single<DeviceDataStore> {
        DeviceDataStore(
            datastore = DevicePreferenceDataSource.createDataStore {
                androidContext().filesDir.resolve(createDataStoreFilename(DevicePreferenceDataSource.NAME)).absolutePath
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
