package org.jdc.kmp.template.ux.directory

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jdc.kmp.template.resources.Resources
import org.jdc.kmp.template.resources.strings.StringResources
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar

@Composable
fun DirectoryScreen() {

    MainAppScaffoldWithNavBar(
        title = "Directory",
        navigationIconVisible = false,
//        actions = { AppBarMenu(appBarMenuItems) },
//        onNavigationClick = { navController.popBackStack() }
    ) {
        DirectoryContent()
    }
}

@Composable
private fun DirectoryContent() {
    Column {
        Text(StringResources.getStrings().about)
        Text(Resources.strings.about)
        Text(Resources.strings.didItXTimes(5))
    }
}
