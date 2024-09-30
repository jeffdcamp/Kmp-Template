
import org.jdc.kmp.template.model.datastore.DatastoreUtil
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.kmp.template.model.datastore.DeviceDataStore
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserDataStore
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.koin.dsl.module

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


//    single<DataStore<Preferences>>(named(DatastoreUtil.USER)) {
//        UserPreferenceDataSource.createDataStore {
//            createDataStoreFilename(DatastoreUtil.USER)
//        }
//    }
//    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) {
//        DevicePreferenceDataSource.createDataStore {
//            createDataStoreFilename(DatastoreUtil.DEVICE)
//        }
//    }


//    single<DataStore<Preferences>>(named(DatastoreUtil.USER)) { createDataStore(createDataStoreFilename(DatastoreUtil.USER)) }
//    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) { createDataStore(createDataStoreFilename(DatastoreUtil.DEVICE)) }
}

//fun createDataStore(filename: String): DataStore<Preferences> = DatastoreUtil.createDataStore(
//    producePath = { File(System.getProperty("java.io.tmpdir"), filename).path }
//)
