package org.jdc.kmp.template.inject

import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.ux.directory.GetDirectoryUiStateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::IndividualRepository)
    factoryOf(::GetDirectoryUiStateUseCase)
}