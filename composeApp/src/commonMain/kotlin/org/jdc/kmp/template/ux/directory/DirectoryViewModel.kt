package org.jdc.kmp.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.dbtools.kmp.commons.compose.navigation.ViewModelNavigation
import org.dbtools.kmp.commons.compose.navigation.ViewModelNavigationImpl

class DirectoryViewModel(
    getDirectoryUiStateUseCase: GetDirectoryUiStateUseCase
) : ViewModel(), ViewModelNavigation by ViewModelNavigationImpl() {
    val uiState: DirectoryUiState = getDirectoryUiStateUseCase(viewModelScope) { navigate(it) }
}
