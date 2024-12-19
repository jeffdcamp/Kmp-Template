package org.jdc.kmp.template.ux.individualedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.dbtools.kmp.commons.compose.navigation.ViewModelNavigation
import org.dbtools.kmp.commons.compose.navigation.ViewModelNavigationImpl

class IndividualEditViewModel(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    private val individualEditRoute = savedStateHandle.toRoute<IndividualEditRoute>(IndividualEditRoute.typeMap())
    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(individualEditRoute.individualId, viewModelScope) { navigate(it) }
}
