package org.jdc.kmp.template.inject

import okio.FileSystem
import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.analytics.DefaultAnalytics
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.model.repository.SettingsRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun getSharedKoinModules(): List<Module> {
    return listOf(
        sharedModule,
        databaseModule,
        datastoreModule,
        coroutineModule,
        fileSystemModule
    )
}

val sharedModule = module {
    singleOf(::UserPreferenceDataSource)
    singleOf(::DevicePreferenceDataSource)

    singleOf(::IndividualRepository)
    singleOf(::SettingsRepository)
    singleOf(::CreateIndividualTestDataUseCase)

    singleOf(::DefaultAnalytics) bind Analytics::class
}

val fileSystemModule = module {
    single { FileSystem.SYSTEM }
}
