package org.jdc.kmp.template.ux

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.dbtools.kmp.commons.compose.navigation3.navigator.TopLevelBackStackNavigator
import org.dbtools.kmp.commons.compose.navigation3.rememberNavigationState
import org.dbtools.kmp.commons.compose.navigation3.toEntries
import org.jdc.kmp.template.ux.directory.DirectoryRoute
import org.jdc.kmp.template.ux.directory.DirectoryScreen
import org.jdc.kmp.template.ux.directory.DirectoryViewModel
import org.jdc.kmp.template.ux.individual.IndividualRoute
import org.jdc.kmp.template.ux.individual.IndividualScreen
import org.jdc.kmp.template.ux.individual.IndividualViewModel
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute
import org.jdc.kmp.template.ux.individualedit.IndividualEditScreen
import org.jdc.kmp.template.ux.individualedit.IndividualEditViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState(
        startRoute = DirectoryRoute,
        topLevelRoutes = NavBarItem.entries.map { it.route }.toSet(),
        navKeySerializer = NavKeyBridgeSerializer
    )

    val navigator = TopLevelBackStackNavigator(navigationState)

    val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
        entry<DirectoryRoute> { DirectoryScreen(navigator, koinViewModel<DirectoryViewModel>()) }
        entry<IndividualRoute> { key ->
            IndividualScreen(navigator, koinViewModel<IndividualViewModel> { parametersOf(key) })
        }
        entry<IndividualEditRoute> { key ->
            IndividualEditScreen(navigator, koinViewModel<IndividualEditViewModel> { parametersOf(key) })
        }
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.pop() }
    )
}
