package org.jdc.kmp.template

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jdc.kmp.template.domain.type.DisplayThemeType
import org.jdc.kmp.template.ui.AppTheme
import org.jdc.kmp.template.ux.MainScreen
import org.jdc.kmp.template.ux.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeApp() {
    val mainViewModel = koinViewModel<MainViewModel>()
    val uiState = mainViewModel.uiState
    val theme by uiState.selectedAppThemeFlow.collectAsState()

    val darkTheme = when(theme?.displayThemeType) {
        DisplayThemeType.SYSTEM_DEFAULT -> isSystemInDarkTheme()
        DisplayThemeType.LIGHT -> false
        DisplayThemeType.DARK -> true
        null -> isSystemInDarkTheme()
    }

//    val dynamicTheme = when(theme?.dynamicTheme) {
//        true -> true
//        else -> false
//    }

    // Update the edge to edge configuration to match the theme
    // This is the same parameters as the default enableEdgeToEdge call, but we manually
    // resolve whether or not to show dark theme using uiState, since it can be different
    // than the configuration's dark theme value based on the user preference.
//            DisposableEffect(darkTheme) {
//                enableEdgeToEdge(
//                    statusBarStyle = SystemBarStyle.auto(
//                        Color.TRANSPARENT,
//                        Color.TRANSPARENT,
//                    ) { darkTheme },
//                    navigationBarStyle = SystemBarStyle.auto(
//                        lightScrim,
//                        darkScrim,
//                    ) { darkTheme },
//                )
//                onDispose {}
//            }

    AppTheme(darkTheme) {
        MainScreen()
    }
}
