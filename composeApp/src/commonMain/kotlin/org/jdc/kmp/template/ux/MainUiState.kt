package org.jdc.kmp.template.ux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MainUiState(
    val selectedAppThemeFlow: StateFlow<SelectedAppTheme?> = MutableStateFlow(null)
)
