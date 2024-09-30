package org.jdc.kmp.template.inject

import androidx.room.RoomDatabase
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.model.repository.SettingsRepository
import org.jdc.kmp.template.ux.directory.GetDirectoryUiStateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<MainDatabase> {
        val builder: RoomDatabase.Builder<MainDatabase> = get()
        MainDatabase.getMainDatabase(builder)
//        builder
//            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
//            .fallbackToDestructiveMigration(dropAllTables = true)
//            .setDriver(BundledSQLiteDriver())
//            .build()
    }

    singleOf(::UserPreferenceDataSource)
    singleOf(::DevicePreferenceDataSource)

    singleOf(::IndividualRepository)
    singleOf(::SettingsRepository)
    singleOf(::CreateIndividualTestDataUseCase)

    factoryOf(::GetDirectoryUiStateUseCase)
}