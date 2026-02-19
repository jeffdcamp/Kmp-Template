package org.jdc.kmp.template

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jdc.kmp.template.domain.type.DisplayThemeType
import org.jdc.kmp.template.ui.AppTheme
import org.jdc.kmp.template.ux.MainScreen
import org.jdc.kmp.template.ux.MainUiState
import org.jdc.kmp.template.ux.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeApp() {
    val mainViewModel = koinViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        mainViewModel.startup()
    }

    val uiState by mainViewModel.uiStateFlow.collectAsState()

    when (val uiState = uiState) {
        MainUiState.Loading -> {}
        is MainUiState.Ready -> {
            val theme = uiState.selectedAppTheme
            val darkTheme = when (theme.displayThemeType) {
                DisplayThemeType.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                DisplayThemeType.LIGHT -> false
                DisplayThemeType.DARK -> true
            }

            AppTheme(darkTheme) {
                MainScreen()
            }
        }
    }
}
