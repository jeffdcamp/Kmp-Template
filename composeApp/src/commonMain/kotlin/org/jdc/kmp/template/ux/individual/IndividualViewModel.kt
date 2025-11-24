package org.jdc.kmp.template.ux.individual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3Impl

class IndividualViewModel(
    getIndividualUiStateUseCase: GetIndividualUiStateUseCase,
    individualRoute: IndividualRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiState: IndividualUiState = getIndividualUiStateUseCase(individualRoute.individualId, viewModelScope) { navigate(it) }
}
