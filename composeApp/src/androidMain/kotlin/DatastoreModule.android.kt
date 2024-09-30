import org.jdc.kmp.template.model.datastore.DatastoreUtil
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.jdc.kmp.template.model.datastore.DeviceDataStore
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserDataStore
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val datastoreModule = module {
    single<UserDataStore> {
        UserDataStore(
            UserPreferenceDataSource.createDataStore {
                androidContext().filesDir.resolve(createDataStoreFilename(DatastoreUtil.USER)).absolutePath
            }
        )
    }

    single<DeviceDataStore> {
        DeviceDataStore(
            DevicePreferenceDataSource.createDataStore {
                androidContext().filesDir.resolve(createDataStoreFilename(DatastoreUtil.DEVICE)).absolutePath
            }
        )
    }

//    single<DataStore<Preferences>>(named(DatastoreUtil.USER)) {
//        UserPreferenceDataSource.createDataStore {
//            androidContext().filesDir.resolve(createDataStoreFilename(DatastoreUtil.USER)).absolutePath
//        }
//    }
//
//    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) {
//        DevicePreferenceDataSource.createDataStore {
//            androidContext().filesDir.resolve(createDataStoreFilename(DatastoreUtil.DEVICE)).absolutePath
//        }
//    }
//    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) { createDataStore(androidContext(), createDataStoreFilename(DatastoreUtil.DEVICE)) }
//    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) { createDataStore(androidContext(), createDataStoreFilename(DatastoreUtil.DEVICE)) }
}

//fun createDataStore(context: Context, filename: String): DataStore<Preferences> = DatastoreUtil.createDataStore(
//    producePath = { context.filesDir.resolve(filename).absolutePath }
//)
