package org.jdc.kmp.template.ux.individual


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.dbtools.kmp.commons.compose.dialog.DialogUiState
import org.jdc.kmp.template.domain.Individual

data class IndividualUiState(
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = MutableStateFlow(null),

    // Data
    val individualFlow: StateFlow<Individual?> = MutableStateFlow(null),

    // Events
    val onEditClick: () -> Unit = {},
    val onDeleteClick: () -> Unit = {},
    val deleteIndividual: () -> Unit = {},
)