package org.jdc.kmp.template.ux.individualedit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dbtools.kmp.commons.compose.dialog.DatePickerDialogUiState
import org.dbtools.kmp.commons.compose.dialog.DialogUiState
import org.dbtools.kmp.commons.compose.dialog.TimePickerDialogUiState
import org.dbtools.kmp.commons.compose.dialog.dismissDialog
import org.dbtools.kmp.commons.compose.navigation.NavigationAction
import org.dbtools.kmp.commons.text.EmailUtil
import org.jdc.kmp.template.analytics.Analytics
import org.jdc.kmp.template.domain.Individual
import org.jdc.kmp.template.domain.inline.Email
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.domain.inline.LastName
import org.jdc.kmp.template.domain.inline.Phone
import org.jdc.kmp.template.domain.type.IndividualType
import org.jdc.kmp.template.model.repository.IndividualRepository
import org.jdc.kmp.template.resources.Resources

class GetIndividualEditUiStateUseCase(
    private val individualRepository: IndividualRepository,
    private val analytics: Analytics,
) {
    private val dialogUiStateFlow = MutableStateFlow<DialogUiState<*>?>(null)

    private var individual = Individual()

    // hold state for Compose views
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

    operator fun invoke(
        individualId: IndividualId?,
        coroutineScope: CoroutineScope,
        navigate: (NavigationAction) -> Unit,
    ): IndividualEditUiState {
        analytics.logEvent(Analytics.EVENT_EDIT_INDIVIDUAL)

        individualId?.let { id ->
            loadIndividual(coroutineScope, id)
        } ?: newIndividual()

        return IndividualEditUiState(
            dialogUiStateFlow = dialogUiStateFlow,

            // Data
            firstNameFlow = firstNameFlow,
            firstNameErrorFlow = firstNameErrorFlow,
            firstNameOnChange = { firstNameFlow.value = it; firstNameErrorFlow.value = null },
            lastNameFlow = lastNameFlow,
            lastNameOnChange = { lastNameFlow.value = it },
            phoneFlow = phoneNumberFlow,
            phoneOnChange = { phoneNumberFlow.value = it },
            emailFlow = emailFlow,
            emailErrorFlow = emailErrorFlow,
            emailOnChange = { emailFlow.value = it; emailErrorFlow.value = null },

            birthDateFlow = birthDateFlow,
            birthDateErrorFlow = birthDateErrorFlow,
            birthDateClick = { showBirthDate(); birthDateErrorFlow.value = null },

            alarmTimeFlow = alarmTimeFlow,
            alarmTimeClick = { showAlarmTime() },

            individualTypeFlow = individualTypeFlow,
            individualTypeErrorFlow = individualTypeErrorFlow,
            individualTypeChange = { individualTypeFlow.value = it; individualTypeErrorFlow.value = null },

            availableFlow = availableFlow,
            availableOnChange = { availableFlow.value = it },

            // Events
            onSaveIndividualClick = { saveIndividual(coroutineScope, navigate) }
        )
    }

    private fun newIndividual() {
        setIndividual(Individual())
    }

    private fun loadIndividual(coroutineScope: CoroutineScope, id: IndividualId) = coroutineScope.launch {
        individualRepository.getIndividual(id)?.let { loadedIndividual ->
            setIndividual(loadedIndividual)
        }
    }

    private fun setIndividual(individual: Individual) {
        this@GetIndividualEditUiStateUseCase.individual = individual

        firstNameFlow.value = individual.firstName?.value.orEmpty()
        lastNameFlow.value = individual.lastName?.value.orEmpty()
        phoneNumberFlow.value = individual.phone?.value.orEmpty()
        emailFlow.value = individual.email?.value.orEmpty()
        birthDateFlow.value = individual.birthDate
        alarmTimeFlow.value = individual.alarmTime
        individualTypeFlow.value = individual.individualType
        availableFlow.value = individual.available
    }

    private fun saveIndividual(coroutineScope: CoroutineScope, navigate: (NavigationAction) -> Unit) = coroutineScope.launch {
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

        navigate(NavigationAction.Pop())
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
            firstNameErrorFlow.value = Resources.getStringResources().required
            allFieldsValid = false
        }

        if (emailFlow.value.isNotBlank() && !EmailUtil.isValidEmailAddress(emailFlow.value)) {
            emailErrorFlow.value = Resources.getStringResources().invalidEmail
            allFieldsValid = false
        }

        val birthDate = birthDateFlow.value
        if (birthDate != null && birthDate >= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) {
            birthDateErrorFlow.value = Resources.getStringResources().invalidBirthDate
            return false
        }

        if (individualTypeFlow.value == IndividualType.CHILD && lastNameFlow.value.isEmpty()) {
            individualTypeErrorFlow.value = Resources.getStringResources().required
            return false
        }

        return allFieldsValid
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun showBirthDate() {
        val todayMs = System.currentTimeMillis()

        dialogUiStateFlow.value = DatePickerDialogUiState(
            localDate = birthDateFlow.value,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis < todayMs
                }
            },
            onConfirm = {
                birthDateFlow.value = it
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }

    private fun showAlarmTime() {
        dialogUiStateFlow.value = TimePickerDialogUiState(
            localTime = alarmTimeFlow.value ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            onConfirm = {
                alarmTimeFlow.value = it
                dismissDialog(dialogUiStateFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateFlow) }
        )
    }
}