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
import dev.icerock.moko.resources.StringResource
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
        navigator = navigator,
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

        val firstName by formFields.firstNameFlow.collectAsState()
        val firstNameError: StringResource? by formFields.firstNameErrorFlow.collectAsState()
        val lastName by formFields.lastNameFlow.collectAsState()
        val phone by formFields.phoneNumberFlow.collectAsState()
        val email by formFields.emailFlow.collectAsState()
        val emailErrorText by formFields.emailErrorFlow.collectAsState()
        val birthDate by formFields.birthDateFlow.collectAsState()
        val birthDateError by formFields.birthDateErrorFlow.collectAsState()
        val alarmTime by formFields.alarmTimeFlow.collectAsState()
        val individualType by formFields.individualTypeFlow.collectAsState()
        val individualTypeError by formFields.individualTypeErrorFlow.collectAsState()

        FlowTextField(stringResource(SharedResources.strings.first_name), firstName, onFirstNameChange, fieldModifier.testTag(IndividualEditScreenFields.FIRST_NAME.name), firstNameError?.let { stringResource(it) })
        FlowTextField(stringResource(SharedResources.strings.last_name), lastName, onLastNameChange, fieldModifier.testTag(IndividualEditScreenFields.LAST_NAME.name))
        FlowTextField(stringResource(SharedResources.strings.phone), phone, onPhoneChange, fieldModifier.testTag(IndividualEditScreenFields.PHONE.name))
        FlowTextField(stringResource(SharedResources.strings.email), email, onEmailChange, fieldModifier.testTag(IndividualEditScreenFields.EMAIL.name), emailErrorText?.let { stringResource(it) })

        DateClickableTextField(
            label = stringResource(SharedResources.strings.birth_date),
            localDate = birthDate,
            localDateToText = { it.toString() },
            onClick = onBirthDateClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.BIRTH_DATE.name),
            errorText = birthDateError?.let { stringResource(it) }
        )

        TimeClickableTextField(
            label = stringResource(SharedResources.strings.alarm_time),
            localTime = alarmTime,
            localTimeToString = { it.toString() },
            onClick = onAlarmTimeClick,
            modifier = fieldModifier.testTag(IndividualEditScreenFields.ALARM_TIME.name)
        )

        DropdownMenuBoxField(
            label = stringResource(SharedResources.strings.individual_type),
            options = IndividualType.entries,
            selectedOption = individualType,
            onOptionSelected = { onIndividualTypeChange(it) },
            optionToText = { it.name },
            errorText = individualTypeError?.let { stringResource(it) },
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
