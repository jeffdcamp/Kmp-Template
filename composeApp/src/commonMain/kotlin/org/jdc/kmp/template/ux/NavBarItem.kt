package org.jdc.kmp.template.ux

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import dev.icerock.moko.resources.StringResource
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.about
import org.jdc.kmp.template.people
import org.jdc.kmp.template.ui.compose.icons.AppIcons
import org.jdc.kmp.template.ui.compose.icons.Group
import org.jdc.kmp.template.ui.compose.icons.Info
import org.jdc.kmp.template.ux.about.AboutRoute
import org.jdc.kmp.template.ux.directory.DirectoryRoute

enum class NavBarItem(
    val unselectedImageVector: ImageVector,
    val selectedImageVector: ImageVector,
    val route: NavKey,
    val label: StringResource,
) {
    PEOPLE(AppIcons.Rounded.Group, AppIcons.Filled.Group, DirectoryRoute, SharedResources.strings.people),
    ABOUT(AppIcons.Rounded.Info, AppIcons.Filled.Info, AboutRoute, SharedResources.strings.about);
}