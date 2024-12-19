package org.jdc.kmp.template

import android.app.Application
import org.jdc.kmp.template.inject.getKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(getKoinModules())
        }

//        KoinInitializer(applicationContext).init()
    }
}