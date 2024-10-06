package org.jdc.kmp.template

import android.app.Application
import org.jdc.kmp.template.inject.KoinInitializer

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}