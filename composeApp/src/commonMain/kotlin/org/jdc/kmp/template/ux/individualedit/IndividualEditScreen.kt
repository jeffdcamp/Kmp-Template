package org.jdc.kmp.template.ux.individualedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.dbtools.kmp.commons.compose.appbar.AppBarMenu
import org.dbtools.kmp.commons.compose.appbar.AppBarMenuItem
import org.dbtools.kmp.commons.compose.dialog.HandleDialogUiState
import org.dbtools.kmp.commons.compose.form.DateClickableTextField
import org.dbtools.kmp.commons.compose.form.DropdownMenuBoxField
import org.dbtools.kmp.commons.compose.form.FlowTextField
import org.dbtools.kmp.commons.compose.form.SwitchField
import org.dbtools.kmp.commons.compose.form.TimeClickableTextField
import org.dbtools.kmp.commons.compose.navigation3.HandleNavigation3
import org.dbtools.kmp.commons.compose.navigation3.navigator.Navigation3Navigator
import org.dbtools.kmp.commons.compose.util.formKeyEventHandler
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.domain.type.IndividualType
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar

@Composable
fun IndividualEditScreen(
    navigator: Navigation3Navigator,
    viewModel: IndividualEditViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    val appBarMenuItems = listOf(
        AppBarMenuItem.Text({ stringResource(SharedResources.strings.save) }) { viewModel.onSaveClick() },
    )

    MainAppScaffoldWithNavBar(
        title = stringResource(SharedResources.strings.edit_individual),
        actions = { AppBarMenu(appBarMenuItems) },
        onNavigationClick = { navigator.pop() },
        hideNavigation = true
    ) {
        when (uiState) {
            IndividualEditUiState.Loading -> {}
            is IndividualEditUiState.Ready -> {
                IndividualEditContent(
                    uiState = uiState,
                    onFirstNameChange = viewModel::onFirstNameChange,
                    onLastNameChange = viewModel::onLastNameChange,
                    onPhoneChange = viewModel::onPhoneChange,
                    onEmailChange = viewModel::onEmailChange,
                    onBirthDateClick = viewModel::onBirthDateClick,
                    onAlarmTimeClick = viewModel::onAlarmTimeClick,
                    onIndividualTypeChange = viewModel::onIndividualTypeChange,
                    onAvailableChange = viewModel::onAvailableChange
                )
            }
        }
    }

    HandleDialogUiState(viewModel.dialogUiStateFlow)

    HandleNavigation3(viewModel, navigator)
}

@Composable
fun IndividualEditContent(
    uiState: IndividualEditUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onBirthDateClick: () -> Unit,
    onAlarmTimeClick: () -> Unit,
    onIndividualTypeChange: (IndividualType) -> Unit,
    onAvailableChange: (Boolean) -> Unit
) {
    if (uiState !is IndividualEditUiState.Ready) {
        return
    }
    val formFields: IndividualEditFormFields = uiState.formFields

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        val focusManager = LocalFocusManager.current

        val fieldModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)

        FlowTextField(stringResource(SharedResources.strings.first_name), formFields.firstNameFlow, onFirstNameChange, fieldModifier.testTag(IndividualEditScreenFields.FIRST_NAME.name),
            formFields.firstNameErrorFlow)
        FlowTextField(stringResource(SharedResources.strings.last_name), formFields.lastNameFlow, onLastNameChange, fieldModifier.testTag(IndividualEditScreenFields.LAST_NAME.name))
        FlowTextField(stringResource(SharedResources.strings.phone), formFields.phoneNumberFlow, onPhoneChange, fieldModifier.testTag(IndividualEditScreenFields.PHONE.name))
        FlowTextField(stringResource(SharedResources.strings.email), formFields.emailFlow, onEmailChange, fieldModifier.testTag(IndividualEditScreenFields.EMAIL.name), formFields.emailErrorFlow)

        DateClickableTextField(
            label = stringResource(SharedResources.strings.birth_date),
            localDateFlow = formFields.birthDateFlow,
            localDateToText = { it.toString() },
            onClick = onBirthDateClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.BIRTH_DATE.name),
            errorTextFlow = formFields.birthDateErrorFlow
        )

        TimeClickableTextField(
            label = stringResource(SharedResources.strings.alarm_time),
            localTimeFlow = formFields.alarmTimeFlow,
            localTimeToString = { it.toString() },
            onClick = onAlarmTimeClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.ALARM_TIME.name)
        )

        DropdownMenuBoxField(
            label = stringResource(SharedResources.strings.individual_type),
            options = IndividualType.entries,
            selectedOptionFlow = formFields.individualTypeFlow,
            onOptionSelected = { onIndividualTypeChange(it) },
            optionToText = { it.name },
            errorTextFlow = formFields.individualTypeErrorFlow,
            modifier = fieldModifier
                .onPreviewKeyEvent { formKeyEventHandler(it, focusManager) }
                .testTag(IndividualEditScreenFields.TYPE.name)
        )

        SwitchField(stringResource(SharedResources.strings.available), formFields.availableFlow, onAvailableChange, fieldModifier.testTag(IndividualEditScreenFields.AVAILABLE.name))
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
