package org.jdc.kmp.template.ux.individualedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3Impl

class IndividualEditViewModel(
    getIndividualEditUiStateUseCase: GetIndividualEditUiStateUseCase,
    individualEditRoute: IndividualEditRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    val uiState: IndividualEditUiState = getIndividualEditUiStateUseCase(individualEditRoute.individualId, viewModelScope) { navigate(it) }
}
