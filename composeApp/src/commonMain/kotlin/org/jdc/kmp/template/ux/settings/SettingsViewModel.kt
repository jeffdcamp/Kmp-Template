package org.jdc.kmp.template.ux.settings

import dev.icerock.moko.resources.compose.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.dbtools.kmp.commons.compose.dialog.DialogUiState
import org.dbtools.kmp.commons.compose.dialog.DropDownMenuDialogUiState
import org.dbtools.kmp.commons.compose.dialog.InputDialogUiState
import org.dbtools.kmp.commons.compose.dialog.RadioDialogDataItem
import org.dbtools.kmp.commons.compose.dialog.RadioDialogDataItems
import org.dbtools.kmp.commons.compose.dialog.RadioDialogUiState
import org.dbtools.kmp.commons.compose.dialog.dismissDialog
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.SharedResources
import getPlatform
import org.jdc.kmp.template.domain.type.DisplayThemeType
import org.jdc.kmp.template.model.repository.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val dialogUiStateMutableFlow = MutableStateFlow<DialogUiState<*>?>(null)
    val dialogUiStateFlow: StateFlow<DialogUiState<*>?> = dialogUiStateMutableFlow

    private val supportsDynamicTheme = getPlatform().name.startsWith("Android")

    val uiStateFlow: StateFlow<SettingsUiState> = combine(
        settingsRepository.themeFlow.map { theme -> themeToString(theme) },
        settingsRepository.lastInstalledVersionCodeFlow.map { versionCode -> versionCode.toString() },
        settingsRepository.rangeFlow.map { it.toString() },
        settingsRepository.dynamicThemeFlow,
        settingsRepository.directorySortByLastNameFlow
    ) { currentThemeTitle, currentLastInstalledVersionCode, range, dynamicTheme, sortByLastName ->
        SettingsUiState.Ready(
            currentThemeTitle = currentThemeTitle,
            currentLastInstalledVersionCode = currentLastInstalledVersionCode,
            range = range,
            dynamicTheme = dynamicTheme,
            showDynamicTheme = supportsDynamicTheme,
            sortByLastName = sortByLastName,
        )
    }.stateInDefault(viewModelScope, SettingsUiState.Loading)

    fun onThemeSettingClick() = viewModelScope.launch {
        val currentTheme = settingsRepository.themeFlow.first()

        val radioItems = DisplayThemeType.entries.map { RadioDialogDataItem(it) { themeToString(it) } }

        dialogUiStateMutableFlow.value = RadioDialogUiState(
            items = RadioDialogDataItems(radioItems, currentTheme),
            title = { stringResource(SharedResources.strings.theme) },
            onConfirm = { theme ->
                settingsRepository.setThemeAsync(theme)
                dismissDialog(dialogUiStateMutableFlow)
            },
            onDismiss = { dismissDialog(dialogUiStateMutableFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateMutableFlow) }
        )
    }

    fun onLastInstalledVersionCodeClick() = viewModelScope.launch {
        val currentValue = settingsRepository.getLastInstalledVersionCode()
        dialogUiStateMutableFlow.value = InputDialogUiState(
            title = { "Version Code" },
            initialTextFieldText = { currentValue.toString() },
            onConfirm = { setLastInstalledVersionCode(it) },
            onDismiss = { dismissDialog(dialogUiStateMutableFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateMutableFlow) },
            minLength = 1
        )
    }

    fun onRangeClick() = viewModelScope.launch {
        val currentValue = settingsRepository.getRange()
        dialogUiStateMutableFlow.value = DropDownMenuDialogUiState(
            title = { "Range" },
            options = SettingsRepository.RANGE_OPTIONS,
            optionToText = { it.toString() },
            initialSelectedOption = currentValue,
            onConfirm = { setRange(it) },
            onDismiss = { dismissDialog(dialogUiStateMutableFlow) },
            onDismissRequest = { dismissDialog(dialogUiStateMutableFlow) },
        )
    }

    private fun setLastInstalledVersionCode(value: String) {
        value.toIntOrNull()?.let {
            settingsRepository.setLastInstalledVersionCodeAsync(it)
        }
        dismissDialog(dialogUiStateMutableFlow)
    }

    private fun setRange(value: Int) {
        settingsRepository.setRangeAsync(value)
        dismissDialog(dialogUiStateMutableFlow)
    }

    fun setSortByLastName(checked: Boolean) {
        settingsRepository.setSortByLastNameAsync(checked)
    }

    fun setDynamicTheme(checked: Boolean) {
        settingsRepository.setDynamicThemeAsync(checked)
    }

    companion object {
        private fun themeToString(theme: DisplayThemeType): String {
            return when (theme) {
                DisplayThemeType.SYSTEM_DEFAULT -> "System Default"
                DisplayThemeType.LIGHT -> "Light"
                DisplayThemeType.DARK -> "Dark"
            }
        }
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Ready(
        val currentThemeTitle: String,
        val currentLastInstalledVersionCode: String,
        val range: String,
        val dynamicTheme: Boolean,
        val showDynamicTheme: Boolean,
        val sortByLastName: Boolean,
    ) : SettingsUiState
}
