package org.jdc.kmp.template.ux.individualedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.dbtools.kmp.commons.compose.appbar.AppBarMenu
import org.dbtools.kmp.commons.compose.appbar.AppBarMenuItem
import org.dbtools.kmp.commons.compose.dialog.HandleDialogUiState
import org.dbtools.kmp.commons.compose.form.DateClickableTextField
import org.dbtools.kmp.commons.compose.form.DropdownMenuBoxField
import org.dbtools.kmp.commons.compose.form.FlowTextField
import org.dbtools.kmp.commons.compose.form.SwitchField
import org.dbtools.kmp.commons.compose.form.TimeClickableTextField
import org.dbtools.kmp.commons.compose.navigation.HandleNavigation
import org.dbtools.kmp.commons.compose.util.formKeyEventHandler
import org.jdc.kmp.template.domain.type.IndividualType
import org.jdc.kmp.template.resources.Resources
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IndividualEditScreen(
    navController: NavController,
    viewModel: IndividualEditViewModel = koinViewModel<IndividualEditViewModel>()
) {
    val uiState = viewModel.uiState

    val appBarMenuItems = listOf(
        AppBarMenuItem.Text({ Resources.strings.save }) { uiState.onSaveIndividualClick() },
    )

    MainAppScaffoldWithNavBar(
        title = Resources.strings.editIndividual,
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navController.popBackStack() },
        hideNavigation = true
    ) {
        IndividualEditContent(viewModel.uiState)
    }

    HandleDialogUiState(uiState.dialogUiStateFlow)

    HandleNavigation(viewModel, navController)
}

@Composable
fun IndividualEditContent(
    uiState: IndividualEditUiState
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        val focusManager = LocalFocusManager.current

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)

        FlowTextField(Resources.strings.firstName, uiState.firstNameFlow, uiState.firstNameOnChange, fieldModifier.testTag(IndividualEditScreenFields.FIRST_NAME.name),
            uiState.firstNameErrorFlow)
        FlowTextField(Resources.strings.lastName, uiState.lastNameFlow, uiState.lastNameOnChange, fieldModifier.testTag(IndividualEditScreenFields.LAST_NAME.name))
        FlowTextField(Resources.strings.phone, uiState.phoneFlow, uiState.phoneOnChange, fieldModifier.testTag(IndividualEditScreenFields.PHONE.name))
        FlowTextField(Resources.strings.email, uiState.emailFlow, uiState.emailOnChange, fieldModifier.testTag(IndividualEditScreenFields.EMAIL.name), uiState.emailErrorFlow)

        DateClickableTextField(
            label = Resources.strings.birthDate,
            localDateFlow = uiState.birthDateFlow,
            localDateToText = { it.toString() },
            onClick = uiState.birthDateClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.BIRTH_DATE.name),
            errorTextFlow = uiState.birthDateErrorFlow
        )

        TimeClickableTextField(
            label = Resources.strings.alarmTime,
            localTimeFlow = uiState.alarmTimeFlow,
            localTimeToString = { it.toString() },
            onClick = uiState.alarmTimeClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.ALARM_TIME.name)
        )

        DropdownMenuBoxField(
            label = Resources.strings.individualType,
            options = IndividualType.entries,
            selectedOptionFlow = uiState.individualTypeFlow,
            onOptionSelected = { uiState.individualTypeChange(it) },
            optionToText = { it.name },
            errorTextFlow = uiState.individualTypeErrorFlow,
            modifier = fieldModifier
                .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
                .testTag(IndividualEditScreenFields.TYPE.name)
        )

        SwitchField(Resources.strings.available, uiState.availableFlow, uiState.availableOnChange, fieldModifier.testTag(IndividualEditScreenFields.AVAILABLE.name))
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
//    val uiState = IndividualEditUiState(
//        firstNameFlow = MutableStateFlow("Jeff")
//    )
//
//    AppTheme {
//        Surface {
//            IndividualEditContent(uiState)
//        }
//    }
//}
