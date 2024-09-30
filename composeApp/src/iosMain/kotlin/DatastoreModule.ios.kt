
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.jdc.kmp.template.model.datastore.DatastoreUtil
import org.jdc.kmp.template.model.datastore.DatastoreUtil.createDataStoreFilename
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

actual val datastoreModule = module {
//    single<DataStore<Preferences>>(named(DatastoreUtil.USER)) { createDataStore(createDataStoreFilename(DatastoreUtil.USER)) }
//    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) { createDataStore(createDataStoreFilename(DatastoreUtil.DEVICE)) }

    single<DataStore<Preferences>>(named(DatastoreUtil.USER)) {
        UserPreferenceDataSource.createDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/${createDataStoreFilename(DatastoreUtil.USER)}"

            createDataStoreFilename(DatastoreUtil.USER)
        }
    }
    single<DataStore<Preferences>>(named(DatastoreUtil.DEVICE)) {
        DevicePreferenceDataSource.createDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/${createDataStoreFilename(DatastoreUtil.DEVICE)}"
        }
    }

}

//fun createDataStore(filename: String): DataStore<Preferences> = DatastoreUtil.createDataStore(
//    producePath = {
//        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
//            directory = NSDocumentDirectory,
//            inDomain = NSUserDomainMask,
//            appropriateForURL = null,
//            create = false,
//            error = null,
//        )
//        requireNotNull(documentDirectory).path + "/$filename"
//    }
//)
