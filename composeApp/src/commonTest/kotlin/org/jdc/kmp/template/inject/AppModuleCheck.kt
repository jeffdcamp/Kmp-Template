package org.jdc.kmp.template.inject

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineDispatcher
import org.jdc.kmp.template.ux.individual.IndividualRoute
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify
import kotlin.test.Test

class AppModuleCheck {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModule() {
        val extraTypes = listOf(
            DataStore::class, // Implementations of DataStore is done in the single<UserDataStore>
            CoroutineDispatcher::class, // Implementations of CoroutineDispatcher is done in the single<AppCoroutineDispatchers>

            // Route items that are supplied by koinViewModel<XxxViewModel> { parametersOf(key) }
            IndividualRoute::class,
            IndividualEditRoute::class
        )

        // Combine all modules into one "wrapper" module for verification.
        // This ensures all modules will know about definitions provided by other modules (Example: appModule can see definitions in datastoreModule)
        module {
            includes(getKoinModules())
        }.verify(extraTypes)
    }
}