package org.jdc.kmp.template

import KoinInitializer
import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}