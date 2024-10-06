package org.jdc.kmp.template.inject

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import org.jdc.kmp.template.model.datastore.DatastoreUtil
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.kmp.template.model.datastore.DeviceDataStore
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserDataStore
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.jdc.kmp.template.ux.directory.DirectoryViewModel
import org.koin.dsl.module
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import java.io.File

actual val databaseModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), MainDatabase.DATABASE_NAME)
        println("DB File: ${dbFile.absolutePath}")
//        val dbFile = File("/home/jeffdcamp/home/Download", MainDatabase.DATABASE_NAME)
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


actual val viewModelModule: Module = module {
    singleOf(::DirectoryViewModel)
}