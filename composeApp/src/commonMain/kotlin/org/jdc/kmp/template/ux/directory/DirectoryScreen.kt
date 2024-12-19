package org.jdc.kmp.template.ux.directory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.dbtools.kmp.commons.compose.appbar.AppBarMenu
import org.dbtools.kmp.commons.compose.appbar.AppBarMenuItem
import org.dbtools.kmp.commons.compose.navigation.HandleNavigation
import org.jdc.kmp.template.resources.Resources
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DirectoryScreen(
    navController: NavHostController,
    viewModel: DirectoryViewModel = koinViewModel<DirectoryViewModel>()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        // icons
        AppBarMenuItem.Icon(Icons.Outlined.Search, { Resources.strings.search }) {},

        // overflow
        AppBarMenuItem.OverflowMenuItem({ Resources.strings.settings }) { uiState.onSettingsClick() }
    )

    MainAppScaffoldWithNavBar(
        title = "Directory",
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { uiState.onNewClick() },
            ) {
                Icon(Icons.Filled.Add, contentDescription = Resources.strings.add)
            }
        }
    ) {

        DirectoryContent(uiState)
    }

    HandleNavigation(viewModel, navController)
}

@Composable
private fun DirectoryContent(uiState: DirectoryUiState) {
    val directoryList by uiState.directoryListFlow.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(directoryList) { individual ->
            ListItem(
                headlineContent = { Text(individual.getFullName()) },
                Modifier
                    .clickable { uiState.onIndividualClick(individual.individualId) },
            )
        }
    }
}
