package org.jdc.kmp.template.ux

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.jdc.kmp.template.ux.directory.DirectoryRoute
import org.jdc.kmp.template.ux.directory.DirectoryScreen
import org.jdc.kmp.template.ux.individual.IndividualRoute
import org.jdc.kmp.template.ux.individual.IndividualScreen
import org.jdc.kmp.template.ux.individual.deepLinks
import org.jdc.kmp.template.ux.individual.typeMap
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute
import org.jdc.kmp.template.ux.individualedit.IndividualEditScreen
import org.jdc.kmp.template.ux.individualedit.typeMap

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
        composable<IndividualRoute>(IndividualRoute.typeMap(), IndividualRoute.deepLinks()) { IndividualScreen(navController) }
        composable<IndividualEditRoute>(IndividualEditRoute.typeMap()) { IndividualEditScreen(navController) }
//        composable<SettingsRoute>(deepLinks = SettingsRoute.deeplinks()) { SettingsScreen(navController) }
//        composable<AboutRoute> { AboutScreen(navController) }
//        composable<TypographyRoute> { TypographyScreen(navController) }
//        composable<AcknowledgmentsRoute> { AcknowledgementScreen(navController) }
//        composable<WorkManagerStatusRoute> { navController.popBackStack() }
    }
}

/**
 * Used for building Compose Navigation deeplinks.
 *
 * Provides common values to build navDeepLink uriPattern (used in AndroidManifest intent-filter)
 */
object NavIntentFilterPart {
    const val DEFAULT_APP_SCHEME = "android-template"
    const val DEFAULT_WEB_SCHEME_HOST = "http://www.mysite.com"
}
