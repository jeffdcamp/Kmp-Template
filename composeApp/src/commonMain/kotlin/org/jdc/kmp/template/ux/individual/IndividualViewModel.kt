package org.jdc.kmp.template.ux.individual

import dev.icerock.moko.resources.compose.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.dbtools.kmp.commons.compose.dialog.DialogUiState
import org.dbtools.kmp.commons.compose.dialog.dismissDialog
import org.dbtools.kmp.commons.compose.dialog.showMessageDialog
import org.dbtools.kmp.commons.compose.navigation3.Navigation3Action
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3Impl
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.domain.Individual
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute

class IndividualViewModel(
    private val individualRepository: IndividualRepository,
    private val analytics: Analytics,
    individualRoute: IndividualRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    private val dialogUiStateMutableFlow = MutableStateFlow<DialogUiState<*>?>(null)
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = dialogUiStateMutableFlow

    val uiStateFlow: StateFlow<IndividualUiState> = individualRepository.getIndividualFlow(individualRoute.individualId).map { individual: Individual? ->
        if (individual != null) {
            IndividualUiState.Ready(individual)
        } else {
            IndividualUiState.Empty
        }
    }.stateInDefault(viewModelScope, IndividualUiState.Loading)

    init {
        analytics.logEvent(Analytics.EVENT_VIEW_INDIVIDUAL)
    }

    fun onDeleteClick(individualId: IndividualId) {
        showMessageDialog(
            dialogUiStateFlow = dialogUiStateMutableFlow,
            text = { stringResource(SharedResources.strings.delete_individual_confirm) },
            confirmButtonText = { stringResource(SharedResources.strings.delete) },
            onConfirm = { deleteIndividual(individualId) },
            dismissButtonText = { stringResource(SharedResources.strings.cancel) },
            onDismiss = { dismissDialog(dialogUiStateMutableFlow) }
        )
    }

    private fun deleteIndividual(individualId: IndividualId) = viewModelScope.launch {
        analytics.logEvent(Analytics.EVENT_DELETE_INDIVIDUAL)
        individualRepository.deleteIndividual(individualId)
        navigate(Navigation3Action.Pop())
    }

    fun onEditClick(individualId: IndividualId) {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        navigate(Navigation3Action.Navigate(IndividualEditRoute(individualId)))
    }
}

sealed interface IndividualUiState {
    data object Loading : IndividualUiState

    data class Ready(
        val individual: Individual,
    ) : IndividualUiState

    data object Empty : IndividualUiState
}
