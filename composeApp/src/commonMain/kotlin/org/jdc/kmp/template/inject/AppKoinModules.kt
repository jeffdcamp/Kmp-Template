package org.jdc.kmp.template.inject

import org.jdc.kmp.template.ux.MainViewModel
import org.jdc.kmp.template.ux.about.AboutViewModel
import org.jdc.kmp.template.ux.directory.DirectoryViewModel
import org.jdc.kmp.template.ux.individual.IndividualViewModel
import org.jdc.kmp.template.ux.individualedit.IndividualEditViewModel
import org.jdc.kmp.template.ux.settings.SettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun getAllKoinModules(): List<Module> {
    return getSharedKoinModules() + listOf(
        viewModelModule
    )
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::DirectoryViewModel)
    viewModelOf(::IndividualViewModel)
    viewModelOf(::IndividualEditViewModel)
    viewModelOf(::AboutViewModel)
    viewModelOf(::SettingsViewModel)
}
