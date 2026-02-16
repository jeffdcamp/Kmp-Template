package org.jdc.kmp.template.ux.individualedit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dbtools.kmp.commons.compose.dialog.DatePickerDialogUiState
import org.dbtools.kmp.commons.compose.dialog.DialogUiState
import org.dbtools.kmp.commons.compose.dialog.TimePickerDialogUiState
import org.dbtools.kmp.commons.compose.dialog.dismissDialog
import org.dbtools.kmp.commons.compose.navigation3.Navigation3Action
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3
import org.dbtools.kmp.commons.compose.navigation3.ViewModelNavigation3Impl
import org.dbtools.kmp.commons.text.EmailUtil
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.domain.Individual
import org.jdc.kmp.template.domain.inline.Email
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.LastName
import org.jdc.kmp.template.domain.inline.Phone
import org.jdc.kmp.template.domain.type.IndividualType
import org.jdc.kmp.template.model.repository.IndividualRepository
import kotlin.time.Clock

class IndividualEditViewModel(
    private val individualRepository: IndividualRepository,
    analytics: Analytics,
    private val individualEditRoute: IndividualEditRoute
) : ViewModel(), ViewModelNavigation3 by ViewModelNavigation3Impl() {
    private val dialogUiStateMutableFlow = MutableStateFlow<DialogUiState<*>?>(null)
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = dialogUiStateMutableFlow

    private val uiStateMutableFlow = MutableStateFlow<IndividualEditUiState>(IndividualEditUiState.Loading)
    val uiStateFlow: StateFlow<IndividualEditUiState> = uiStateMutableFlow

    private var individual = Individual()

    private val firstNameFlow = MutableStateFlow("")
    private val firstNameErrorFlow = MutableStateFlow<String?>(null)
    private val lastNameFlow = MutableStateFlow("")
    private val phoneNumberFlow = MutableStateFlow("")
    private val emailFlow = MutableStateFlow("")
    private val emailErrorFlow = MutableStateFlow<String?>(null)
    private val birthDateFlow = MutableStateFlow<LocalDate?>(null)
    private val birthDateErrorFlow = MutableStateFlow<String?>(null)
    private val alarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    private val individualTypeFlow = MutableStateFlow(IndividualType.UNKNOWN)
    private val individualTypeErrorFlow = MutableStateFlow<String?>(null)
    private val availableFlow = MutableStateFlow(false)

    init {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)
        loadIndividual()
    }

    private fun loadIndividual() = viewModelScope.launch {
        if (individualEditRoute.individualId != null) {
            individualRepository.getIndividual(individualEditRoute.individualId)?.let { loadedIndividual ->
                setIndividual(loadedIndividual)
                individual = loadedIndividual
            }
        }

        uiStateMutableFlow.value = IndividualEditUiState.Ready(
            IndividualEditFormFields(
                firstNameFlow = firstNameFlow,
                firstNameErrorFlow = firstNameErrorFlow,
                lastNameFlow = lastNameFlow,
                phoneNumberFlow = phoneNumberFlow,
                emailFlow = emailFlow,
                emailErrorFlow = emailErrorFlow,
                birthDateFlow = birthDateFlow,
                birthDateErrorFlow = birthDateErrorFlow,
                alarmTimeFlow = alarmTimeFlow,
                individualTypeFlow = individualTypeFlow,
                individualTypeErrorFlow = individualTypeErrorFlow,
                availableFlow = availableFlow,
            )
        )
    }

    private fun setIndividual(loadedIndividual: Individual) {
        firstNameFlow.value = loadedIndividual.firstName?.value.orEmpty()
        lastNameFlow.value = loadedIndividual.lastName?.value.orEmpty()
        phoneNumberFlow.value = loadedIndividual.phone?.value.orEmpty()
        emailFlow.value = loadedIndividual.email?.value.orEmpty()
        birthDateFlow.value = loadedIndividual.birthDate
        alarmTimeFlow.value = loadedIndividual.alarmTime
        individualTypeFlow.value = loadedIndividual.individualType
        availableFlow.value = loadedIndividual.available
    }

    fun onFirstNameChange(value: String) {
        firstNameFlow.value = value
        firstNameErrorFlow.value = null
    }

    fun onLastNameChange(value: String) {
        lastNameFlow.value = value
    }

    fun onPhoneChange(value: String) {
        phoneNumberFlow.value = value
    }

    fun onEmailChange(value: String) {
        emailFlow.value = value
        emailErrorFlow.value = null
    }

    fun onBirthDateClick() {
        showBirthDate()
        birthDateErrorFlow.value = null
    }

    fun onAlarmTimeClick() {
        showAlarmTime()
    }

    fun onIndividualTypeChange(value: IndividualType) {
        individualTypeFlow.value = value
        individualTypeErrorFlow.value = null
    }

    fun onAvailableChange(value: Boolean) {
        availableFlow.value = value
    }

    fun onSaveClick() = viewModelScope.launch {
        if (!validateAllFields()) {
            return@launch
        }

        individualRepository.saveIndividual(individual.copy(
            firstName = valueOrNull(firstNameFlow.value)?.let { FirstName(it) },
            lastName = valueOrNull(lastNameFlow.value)?.let { LastName(it) },
            phone = valueOrNull(phoneNumberFlow.value)?.let { Phone(it) },
            email = valueOrNull(emailFlow.value)?.let { Email(it) },
            birthDate = birthDateFlow.value,
            alarmTime = alarmTimeFlow.value,
            individualType = individualTypeFlow.value,
            available = availableFlow.value,
        ))

        navigate(Navigation3Action.Pop())
    }

    private fun valueOrNull(value: String): String? {
        return value.ifBlank {
            null
        }
    }

    private fun resetErrors() {
        firstNameErrorFlow.value = null
        emailErrorFlow.value = null
        individualTypeErrorFlow.value = null
    }

    private fun validateAllFields(): Boolean {
        resetErrors()
        var allFieldsValid = true

        if (firstNameFlow.value.isBlank()) {
            firstNameErrorFlow.value = SharedResources.strings.required.localized()
            allFieldsValid = false
        }

        if (emailFlow.value.isNotBlank() && !EmailUtil.isValidEmailAddress(emailFlow.value)) {
            emailErrorFlow.value = SharedResources.strings.invalid_email.localized()
            allFieldsValid = false
        }

        val birthDate = birthDateFlow.value
        if (birthDate != null && birthDate >= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) {
            birthDateErrorFlow.value = SharedResources.strings.invalid_birth_date.localized()
            return false
        }

        if (individualTypeFlow.value == IndividualType.CHILD && lastNameFlow.value.isEmpty()) {
            individualTypeErrorFlow.value = SharedResources.strings.required.localized()
            return false
        }

        return allFieldsValid
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun showBirthDate() {
        val todayMs = System.currentTimeMillis()

        dialogUiStateMutableFlow.value = DatePickerDialogUiState(
            localDate = birthDateFlow.value,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis < todayMs
                }
            },
            onConfirm = {
                birthDateFlow.value = it
                dismissDialog(dialogUiStateMutableFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateMutableFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateMutableFlow) }
        )
    }

    private fun showAlarmTime() {
        dialogUiStateMutableFlow.value = TimePickerDialogUiState(
            localTime = alarmTimeFlow.value ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            onConfirm = {
                alarmTimeFlow.value = it
                dismissDialog(dialogUiStateMutableFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateMutableFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateMutableFlow) }
        )
    }
}

sealed interface IndividualEditUiState {
    data object Loading : IndividualEditUiState
    data class Ready(val formFields: IndividualEditFormFields) : IndividualEditUiState
}

data class IndividualEditFormFields(
    val firstNameFlow: MutableStateFlow<String>,
    val firstNameErrorFlow: MutableStateFlow<String?>,
    val lastNameFlow: MutableStateFlow<String>,
    val phoneNumberFlow: MutableStateFlow<String>,
    val emailFlow: MutableStateFlow<String>,
    val emailErrorFlow: MutableStateFlow<String?>,
    val birthDateFlow: MutableStateFlow<LocalDate?>,
    val birthDateErrorFlow: MutableStateFlow<String?>,
    val alarmTimeFlow: MutableStateFlow<LocalTime?>,
    val individualTypeFlow: MutableStateFlow<IndividualType>,
    val individualTypeErrorFlow: MutableStateFlow<String?>,
    val availableFlow: MutableStateFlow<Boolean>,
)
