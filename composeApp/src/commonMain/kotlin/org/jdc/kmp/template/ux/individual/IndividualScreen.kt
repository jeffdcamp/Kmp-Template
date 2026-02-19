package org.jdc.kmp.template.ux.individual

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.dbtools.kmp.commons.compose.appbar.AppBarMenu
import org.dbtools.kmp.commons.compose.appbar.AppBarMenuItem
import org.dbtools.kmp.commons.compose.dialog.HandleDialogUiState
import org.dbtools.kmp.commons.compose.form.TextWithTitle
import org.dbtools.kmp.commons.compose.navigation3.HandleNavigation3
import org.dbtools.kmp.commons.compose.navigation3.navigator.Navigation3Navigator
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.domain.Individual
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar

@Composable
fun IndividualScreen(
    navigator: Navigation3Navigator,
    viewModel: IndividualViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    val individualId = if (uiState is IndividualUiState.Ready) (uiState as IndividualUiState.Ready).individual.id else null

    val appBarMenuItems = listOf(
        AppBarMenuItem.Icon(Icons.Outlined.Edit, { stringResource(SharedResources.strings.edit) }) { individualId?.let { viewModel.onEditClick(it) } },
        AppBarMenuItem.Icon(Icons.Outlined.Delete, { stringResource(SharedResources.strings.delete) }) { individualId?.let { viewModel.onDeleteClick(it) } }
    )

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(SharedResources.strings.individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() },
    ) {
        IndividualContent(uiState)
    }

    HandleDialogUiState(viewModel.dialogUiStateFlow)
    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun IndividualContent(uiState: IndividualUiState) {
    when (uiState) {
        IndividualUiState.Loading -> {}
        is IndividualUiState.Ready -> { IndividualSummary(uiState.individual) }
        IndividualUiState.Empty -> {}
    }
}

@Composable
private fun IndividualSummary(individual: Individual) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp)
    ) {
        TextWithTitle(individual.getFullName(), textStyle = MaterialTheme.typography.headlineSmall)
        TextWithTitle(individual.phone?.value, stringResource(SharedResources.strings.phone))
        TextWithTitle(individual.email?.value, stringResource(SharedResources.strings.email))
//        TextWithTitle(DateUiUtil.getLocalDateText(LocalContext.current, individual.birthDate), stringResource(SharedResources.strings.birthDate))
//        TextWithTitle(DateUiUtil.getLocalTimeText(LocalContext.current, individual.alarmTime), stringResource(SharedResources.strings.alarmTime))
    }
}
