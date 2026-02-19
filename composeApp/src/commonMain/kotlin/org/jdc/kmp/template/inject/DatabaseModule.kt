package org.jdc.kmp.template.inject

import androidx.room.RoomDatabase
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val databaseBuilderModule: Module
expect val datastoreModule: Module
expect val coroutineModule: Module

val databaseModule = module {
    includes(databaseBuilderModule)

    single<MainDatabase> {
        val builder: RoomDatabase.Builder<MainDatabase> = get()
        MainDatabase.getDatabase(builder)
    }
}
