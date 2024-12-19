package org.jdc.kmp.template.ux.individual

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.dbtools.kmp.commons.compose.dialog.DialogUiState
import org.dbtools.kmp.commons.compose.dialog.dismissDialog
import org.dbtools.kmp.commons.compose.dialog.showMessageDialog
import org.dbtools.kmp.commons.compose.navigation.NavigationAction
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.resources.Resources
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute


class GetIndividualUiStateUseCase(
    private val individualRepository: IndividualRepository,
    private val analytics: Analytics,
) {
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    operator fun invoke(
        individualId: IndividualId,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit,
    ): IndividualUiState {
        return IndividualUiState(
            dialogUiStateFlow = dialogUiStateFlow,

            individualFlow = individualRepository.getIndividualFlow(individualId).stateInDefault(coroutineScope, null),

            onEditClick = { editIndividual(individualId, navigate) },
            onDeleteClick = { onDeleteClick(individualId, coroutineScope, navigate) },
            deleteIndividual = { deleteIndividual(individualId, coroutineScope, navigate) },
        )
    }

    private fun onDeleteClick(individualId: IndividualId, coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) {
        showMessageDialog(
            dialogUiStateFlow,
            text = { Resources.strings.deleteIndividualConfirm },
            onConfirm = { deleteIndividual(individualId, coroutineScope, navigate) },
            onDismiss = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun deleteIndividual(individualId: IndividualId, coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) = coroutineScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        navigate(NavigationAction.Pop())
    }

    private fun editIndividual(individualId: IndividualId, navigate: (NavigationAction) -> Unit) {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(NavigationAction.Navigate(IndividualEditRoute(individualId)))
    }
}