package org.jdc.kmp.template.ux

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.People
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import dev.icerock.moko.resources.StringResource
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.ux.about.AboutRoute
import org.jdc.kmp.template.ux.directory.DirectoryRoute

enum class NavBarItem(
    val unselectedImageVector: ImageVector,
    val selectedImageVector: ImageVector,
    val route: NavKey,
    val label: StringResource,
) {
    PEOPLE(Icons.Outlined.People, Icons.Filled.People, DirectoryRoute, SharedResources.strings.people),
    ABOUT(Icons.Outlined.Info, Icons.Filled.Info, AboutRoute, SharedResources.strings.about);
}