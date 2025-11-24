package org.jdc.kmp.template.ux

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.dbtools.kmp.commons.compose.navigation3.navigator.Navigation3Navigator
import org.dbtools.kmp.commons.compose.navigation3.navigator.TopLevelBackStackNavigator
import org.dbtools.kmp.commons.ext.stateInDefault
import org.jdc.kmp.template.domain.type.DisplayThemeType
import org.jdc.kmp.template.domain.usecase.CreateIndividualTestDataUseCase
import org.jdc.kmp.template.model.repository.SettingsRepository
import org.jdc.kmp.template.ux.directory.DirectoryRoute

class MainViewModel(
    settingsRepository: SettingsRepository,
    private val createIndividualTestDataUseCase: CreateIndividualTestDataUseCase
) : ViewModel() {
    val uiState = MainUiState(
        selectedAppThemeFlow = combine(
            settingsRepository.themeFlow.stateInDefault(viewModelScope, null),
            settingsRepository.dynamicThemeFlow.stateInDefault(viewModelScope, null)
        ) { displayThemeType, dynamicTheme ->
            SelectedAppTheme(displayThemeType ?: DisplayThemeType.SYSTEM_DEFAULT, dynamicTheme ?: false)
        }.stateInDefault(viewModelScope, null)
    )

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

data class SelectedAppTheme(val displayThemeType: DisplayThemeType, val dynamicTheme: Boolean)
