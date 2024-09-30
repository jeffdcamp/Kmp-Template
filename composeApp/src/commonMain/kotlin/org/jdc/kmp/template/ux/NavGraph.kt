package org.jdc.kmp.template.ux

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.jdc.kmp.template.ux.directory.DirectoryRoute
import org.jdc.kmp.template.ux.directory.DirectoryScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Debug navigation
//    navController.addOnDestinationChangedListener(NavUriLogger())

    NavHost(
        navController = navController,
        startDestination = DirectoryRoute
    ) {
        composable<DirectoryRoute> { DirectoryScreen(navController) }
//        DirectoryRoute.addNavigationRoute(this) { DirectoryScreen(navController) }
//        IndividualRoute.addNavigationRoute(this) { IndividualScreen(navController) }
//        IndividualEditRoute.addNavigationRoute(this) { IndividualEditScreen(navController) }
//
//        SettingsRoute.addNavigationRoute(this) { SettingsScreen(navController) }
//        AboutRoute.addNavigationRoute(this) { AboutScreen(navController) }
//
//        TypographyRoute.addNavigationRoute(this) { TypographyScreen(navController) }
//
//        AcknowledgmentsRoute.addNavigationRoute(this) { AcknowledgementScreen(navController) }
//
//        WorkManagerStatusRoute.addNavigationRoute(this) { WorkManagerStatusScreen { navController.popBackStack() } }
    }
}
