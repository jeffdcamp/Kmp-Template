package org.jdc.kmp.template.ux.directory

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import org.jdc.kmp.template.model.repository.IndividualRepository

class GetDirectoryUiStateUseCase(
    private val individualRepository: IndividualRepository,
) {
    operator fun invoke(
        coroutineScope: CoroutineScope,
    ): DirectoryUiState {
        return DirectoryUiState(
//            directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(coroutineScope, emptyList()),
            onNewClicked = {
                Logger.e { "Hello World!" }
//                navigate(NavigationAction.Navigate(IndividualEditRoute.createRoute()))
            },
//            onIndividualClicked = { individualId -> navigate(NavigationAction.Navigate(IndividualRoute.createRoute(individualId))) },
            onSettingsClicked = {
//                navigate(NavigationAction.Navigate(SettingsRoute.createRoute()))
            }
        )
    }
}