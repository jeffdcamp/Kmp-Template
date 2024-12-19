package org.jdc.kmp.template.ux.individual

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.dbtools.kmp.commons.compose.navigation.ViewModelNavigation
import org.dbtools.kmp.commons.compose.navigation.ViewModelNavigationImpl

class IndividualViewModel(
    getIndividualUiStateUseCase: GetIndividualUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    private val individualRoute = savedStateHandle.toRoute<IndividualRoute>(IndividualRoute.typeMap())
    val uiState: IndividualUiState = getIndividualUiStateUseCase(individualRoute.individualId, viewModelScope) { navigate(it) }
}
