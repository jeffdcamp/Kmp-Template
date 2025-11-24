package org.jdc.kmp.template.ux.directory

import kotlinx.coroutines.CoroutineScope
import org.dbtools.kmp.commons.compose.navigation3.Navigation3Action
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.ux.individual.IndividualRoute
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute

class GetDirectoryUiStateUseCase(
    private val individualRepository: IndividualRepository,
) {
    operator fun invoke(
        coroutineScope: CoroutineScope,
        navigate: (Navigation3Action) -> Unit,
    ): DirectoryUiState {
        return DirectoryUiState(
            directoryListFlow = individualRepository.getDirectoryListFlow().stateInDefault(coroutineScope, emptyList()),
            onNewClick = { navigate(Navigation3Action.Navigate(IndividualEditRoute())) },
            onIndividualClick = { individualId -> navigate(Navigation3Action.Navigate(IndividualRoute(individualId))) },
//            onSettingsClick = { navigate(NavigationAction.Navigate(SettingsRoute)) }
            onSettingsClick = {  }
        )
    }
}