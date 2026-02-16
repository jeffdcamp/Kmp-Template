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
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IndividualScreen(
    navigator: Navigation3Navigator,
    viewModel: IndividualViewModel = koinViewModel<IndividualViewModel>()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Icon(Icons.Outlined.Edit, { stringResource(SharedResources.strings.edit) }) { uiState.onEditClick() },
        AppBarMenuItem.Icon(Icons.Outlined.Delete, { stringResource(SharedResources.strings.delete) }) { uiState.onDeleteClick() }
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(SharedResources.strings.individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() },
    ) {
        IndividualContent(uiState)
    }

    HandleDialogUiState(uiState.dialogUiStateFlow)
    HandleNavigation3(viewModel, navigator)
}

@Composable
private fun IndividualContent(uiState: IndividualUiState) {
    val individual by uiState.individualFlow.collectAsState()
    individual?.let { IndividualSummary(it) }
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

enum class IndividualEditScreenFields {
    FIRST_NAME,
    LAST_NAME,
    PHONE,
    EMAIL,
    BIRTH_DATE,
    ALARM_TIME,
    TYPE,
    AVAILABLE
}

//@PreviewDefault
//@Composable
//private fun Preview() {
//    AppTheme {
//        Surface {
//            IndividualSummary(
//                individual = Individual(
//                    firstName = FirstName("Jeff"),
//                    lastName = LastName("Campbell"),
//                    phone = Phone("801-555-0001"),
//                    email = Email("bob@bob.com")
//                )
//            )
//        }
//    }
//}
