package org.jdc.kmp.template.ux.directory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jdc.kmp.template.domain.Individual
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.util.ext.stateInDefault

class GetDirectoryUiStateUseCase(
    private val individualRepository: IndividualRepository,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase,
) {
    operator fun invoke(
        coroutineScope: CoroutineScope,
    ): DirectoryUiState {
        return DirectoryUiState(
            directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(coroutineScope, emptyList()),
            onNewClick = { onNewClick(coroutineScope)

//                navigate(NavigationAction.Navigate(IndividualEditRoute.createRoute()))
            },
//            onIndividualClick = { individualId -> navigate(NavigationAction.Navigate(IndividualRoute.createRoute(individualId))) },
            onSettingsClick = {
//                navigate(NavigationAction.Navigate(SettingsRoute.createRoute()))
            }
        )
    }

    private fun onNewClick(coroutineScope: CoroutineScope) = GlobalScope.launch {
        individualRepository.saveIndividual(Individual(firstName = FirstName("Jeff")))
//        runBlocking { createIndividualTestDataUseCase.invoke() }
    }
}