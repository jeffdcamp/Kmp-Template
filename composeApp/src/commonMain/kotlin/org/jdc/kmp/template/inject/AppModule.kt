package org.jdc.kmp.template.inject

import androidx.room.RoomDatabase
import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.analytics.DefaultAnalytics
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.model.repository.SettingsRepository
import org.jdc.kmp.template.ux.MainViewModel
import org.jdc.kmp.template.ux.directory.DirectoryViewModel
import org.jdc.kmp.template.ux.directory.GetDirectoryUiStateUseCase
import org.jdc.kmp.template.ux.individual.GetIndividualUiStateUseCase
import org.jdc.kmp.template.ux.individual.IndividualViewModel
import org.jdc.kmp.template.ux.individualedit.GetIndividualEditUiStateUseCase
import org.jdc.kmp.template.ux.individualedit.IndividualEditViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<MainDatabase> {
        val builder: RoomDatabase.Builder<MainDatabase> = get()
        MainDatabase.getDatabase(builder)
    }

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

//    singleOf(::DefaultAnalytics)
    single<Analytics> {
        DefaultAnalytics()
    }
}

fun getKoinModules(): List<org.koin.core.module.Module> {
    return listOf(
        databaseModule,
        datastoreModule,
        coroutineModule,
        appModule
    )
}