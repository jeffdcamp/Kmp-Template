package org.jdc.kmp.template.ux.directory

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.jdc.kmp.template.inject.koinViewModel
import org.jdc.kmp.template.resources.Resources
import org.jdc.kmp.template.resources.strings.StringResources
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DirectoryScreen(
    navController: NavHostController,
    viewModel: DirectoryViewModel = koinViewModel<DirectoryViewModel>()
) {
    val uiState = viewModel.uiState

    MainAppScaffoldWithNavBar(
        title = "Directory",
        navigationIconVisible = false,
//        actions = { AppBarMenu(appBarMenuItems) },
//        onNavigationClick = { navController.popBackStack() }
    ) {
        DirectoryContent(uiState)
    }
}

@Composable
private fun DirectoryContent(uiState: DirectoryUiState) {
    Column {
        Text(StringResources.getStrings().about)
        Text(Resources.strings.about)
        Text(Resources.strings.didItXTimes(5, "Jeff"))

//        Button(uiState.onNewClicked) { Text("Hello World") }
    }
}

@Preview
@Composable
fun Preview(modifier: Modifier = Modifier) {
    val uiState = DirectoryUiState(

    )

    DirectoryContent(uiState)
}
