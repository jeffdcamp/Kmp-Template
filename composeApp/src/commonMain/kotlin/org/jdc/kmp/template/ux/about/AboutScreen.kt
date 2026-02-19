package org.jdc.kmp.template.ux.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.dbtools.kmp.commons.compose.appbar.AppBarMenu
import org.dbtools.kmp.commons.compose.appbar.AppBarMenuItem
import org.dbtools.kmp.commons.compose.navigation3.HandleNavigation3
import org.dbtools.kmp.commons.compose.navigation3.navigator.Navigation3Navigator
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.ui.AppTheme
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar

@Composable
fun AboutScreen(
    navigator: Navigation3Navigator,
    viewModel: AboutViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    val appBarMenuItems = listOf(
        AppBarMenuItem.OverflowMenuItem({ stringResource(SharedResources.strings.acknowledgments) } ) { viewModel.onLicensesClick() }
    )

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(SharedResources.strings.about),
        navigationIconVisible = false,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() }
    ) {
        when (val uiState = uiState) {
            AboutUiState.Loading -> {}
            is AboutUiState.Ready -> {
                AboutScreenContent(
                    uiState = uiState,
                    createSampleData = { viewModel.createSampleData() },
                )

            }
        }
    }

    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun AboutScreenContent(
    uiState: AboutUiState.Ready,
    createSampleData: () -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        ApplicationAboutTitle()

        HorizontalDivider(Modifier.padding(top = 16.dp, bottom = 16.dp))
        RestServicesStatus(uiState.resetServiceEnabled)
        TestButton("Create Database") { createSampleData() }
    }
}

@Composable
private fun ApplicationAboutTitle() {
    Column {
        Text(
            stringResource(SharedResources.strings.about),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            "1.0.0",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun RestServicesStatus(restServicesEnabled: Boolean) {
    Row {
        Text("Rest Services Enabled: ")
        Text(text = restServicesEnabled.toString(), color = AppTheme.extendedColors.customColorA.color)
    }
}

@Composable
private fun TestButton(text: String, block: () -> Unit) {
    Button(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        onClick = { block() }) {
        Text(text)
    }
}
