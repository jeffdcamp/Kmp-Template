package org.jdc.kmp.template.inject

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(
                viewModelModule,
                databaseModule,
                datastoreModule,
                coroutineModule,
                appModule
            )
        }
    }
}