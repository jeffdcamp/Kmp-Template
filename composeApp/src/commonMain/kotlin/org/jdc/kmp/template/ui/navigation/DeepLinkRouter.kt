package org.jdc.kmp.template.ui.navigation

import androidx.navigation3.runtime.NavKey
import org.dbtools.kmp.commons.compose.navigation3.deeplink.BaseDeepLinkRouter
import org.dbtools.kmp.commons.compose.navigation3.deeplink.RouteMatcher
import org.jdc.kmp.template.ux.directory.DirectoryRouteMatcher
import org.jdc.kmp.template.ux.individual.IndividualRouteMatcher
import org.jdc.kmp.template.ux.individualedit.IndividualEditRouteMatcher

object DeepLinkRouter : BaseDeepLinkRouter() {
    override fun getMatchers(): List<RouteMatcher<out NavKey>> = listOf(
        DirectoryRouteMatcher,
        IndividualRouteMatcher,
        IndividualEditRouteMatcher,
    )
}

fun NavKey.toUri(): String = DeepLinkRouter.toUri(this)
fun String.toRoute(): NavKey? = DeepLinkRouter.fromUri(this)
