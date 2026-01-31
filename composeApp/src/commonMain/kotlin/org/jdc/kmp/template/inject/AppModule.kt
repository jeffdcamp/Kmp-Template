package org.jdc.kmp.template.inject

import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.analytics.DefaultAnalytics
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.model.repository.SettingsRepository
import org.jdc.kmp.template.ux.MainViewModel
import org.jdc.kmp.template.ux.directory.DirectoryViewModel
import org.jdc.kmp.template.ux.directory.GetDirectoryUiStateUseCase
import org.jdc.kmp.template.ux.individual.GetIndividualUiStateUseCase
import org.jdc.kmp.template.ux.individual.IndividualViewModel
import org.jdc.kmp.template.ux.individualedit.GetIndividualEditUiStateUseCase
import org.jdc.kmp.template.ux.individualedit.IndividualEditViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun getKoinModules(): List<Module> {
    return listOf(
        appModule,
        databaseModule,
        datastoreModule,
        coroutineModule
    )
}

val appModule = module {
    singleOf(::UserPreferenceDataSource)
    singleOf(::DevicePreferenceDataSource)

    singleOf(::IndividualRepository)
    singleOf(::SettingsRepository)
    singleOf(::CreateIndividualTestDataUseCase)

    factoryOf(::GetDirectoryUiStateUseCase)
    factoryOf(::GetIndividualUiStateUseCase)
    factoryOf(::GetIndividualEditUiStateUseCase)

    viewModelOf(::MainViewModel)
    viewModelOf(::DirectoryViewModel)
    viewModelOf(::IndividualViewModel)
    viewModelOf(::IndividualEditViewModel)

    singleOf(::DefaultAnalytics) bind Analytics::class
}
