package org.jdc.kmp.template.ux

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.domain.type.DisplayThemeType
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.repository.SettingsRepository

class MainViewModel(
    settingsRepository: SettingsRepository,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase
) : ViewModel() {
    val uiStateFlow: StateFlow<MainUiState> = combine(
        settingsRepository.themeFlow,
        settingsRepository.dynamicThemeFlow
    ) { displayThemeType, dynamicTheme ->
        MainUiState.Ready(SelectedAppTheme(displayThemeType, dynamicTheme))
    }.stateInDefault(viewModelScope, MainUiState.Loading)

    private var startupComplete = false
    fun startup() = viewModelScope.launch {
        if (startupComplete) {
            return@launch
        }

        // run any startup/initialization code here (NOTE: these tasks should NOT exceed 1000ms (per Google Guidelines))
        Logger.i { "Startup task..." }

        // Startup finished
        Logger.i { "Startup finished" }

        startupComplete = true
    }

    @VisibleForTesting
    suspend fun createSampleData() {
        createIndividualTestDataUseCase()
    }
}

sealed interface MainUiState {
    data object Loading : MainUiState

    data class Ready(
        val selectedAppTheme: SelectedAppTheme,
    ) : MainUiState
}

data class SelectedAppTheme(val displayThemeType: DisplayThemeType, val dynamicTheme: Boolean)
