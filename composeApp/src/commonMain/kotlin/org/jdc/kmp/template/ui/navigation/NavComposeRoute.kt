@file:Suppress("MemberVisibilityCanBePrivate")

package org.jdc.kmp.template.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

abstract class NavComposeRoute {
    /**
     * Route definition
     */
    abstract val routeDefinition: NavRouteDefinition

    /**
     * Route used when navigating
     *
     * Each class that implements NavActivityRoute should implement this method with the needed args to fulfil the routeDefinition
     */
    // fun createRoute(): String = routeDefinition

    /**
     * List of arguments to associate with destination
     *
     * see: https://developer.android.com/develop/ui/compose/navigation#nav-with-args
     */
    abstract fun getArguments(): List<NamedNavArgument>

    /**
     * List of deep links to associate with the destinations
     *
     * see: https://developer.android.com/develop/ui/compose/navigation#deeplinks
     */
    open fun getDeepLinks(): List<NavDeepLink> = emptyList()

    /**
     * Add the [Composable] to the [NavGraphBuilder]
     *
     * @param navGraphBuilder navGraphBuilder that the new NavComposeRoute will be added to
     * @param enterTransition callback to determine the destination's enter transition
     * @param exitTransition callback to determine the destination's exit transition
     * @param popEnterTransition callback to determine the destination's popEnter transition
     * @param popExitTransition callback to determine the destination's popExit transition
     * @param content composable for the destination
     */
    fun addNavigationRoute(
        navGraphBuilder: NavGraphBuilder,
        enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
        exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
        popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
        popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
    ) {
        navGraphBuilder.composable(
            route = routeDefinition.value,
            arguments = getArguments(),
            deepLinks = getDeepLinks(),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content
        )
    }
}

/**
 * Simple Route that requires no arguments
 */
@Suppress("UnnecessaryAbstractClass") // this is an edge case... allow
abstract class SimpleNavComposeRoute(routeDefinitionValue: String) : NavComposeRoute() {
    override val routeDefinition: NavRouteDefinition = NavRouteDefinition(routeDefinitionValue)

    /**
     * Route used when navigating
     */
    fun createRoute(): NavRoute = routeDefinition.asNavRoute()

    override fun getArguments(): List<NamedNavArgument> = emptyList()

    override fun getDeepLinks(): List<NavDeepLink> = emptyList()
}
