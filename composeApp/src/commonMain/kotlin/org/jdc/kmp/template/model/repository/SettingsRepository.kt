package org.jdc.kmp.template.model.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jdc.kmp.template.domain.type.DisplayThemeType
import org.jdc.kmp.template.model.datastore.DevicePreferenceDataSource
import org.jdc.kmp.template.model.datastore.UserPreferenceDataSource

class SettingsRepository(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val devicePreferenceDataSource: DevicePreferenceDataSource,
) {
    val themeFlow: Flow<DisplayThemeType> get() = devicePreferenceDataSource.themePref.flow
    fun setThemeAsync(theme: DisplayThemeType) = GlobalScope.launch { devicePreferenceDataSource.themePref.setValue(theme) }

    val dynamicThemeFlow: Flow<Boolean> get() = devicePreferenceDataSource.dynamicThemePref.flow
    fun setDynamicThemeAsync(enabled: Boolean) = GlobalScope.launch { devicePreferenceDataSource.dynamicThemePref.setValue(enabled) }

    val directorySortByLastNameFlow: Flow<Boolean> get() = userPreferenceDataSource.directorySortByLastNamePref.flow
    fun setSortByLastNameAsync(sortAscending: Boolean) = GlobalScope.launch { userPreferenceDataSource.directorySortByLastNamePref.setValue(sortAscending) }

    val isDeveloperModeEnabledFlow: Flow<Boolean> get() = devicePreferenceDataSource.developerModePref.flow
    suspend fun isDeveloperModeEnabled(): Boolean = devicePreferenceDataSource.developerModePref.flow.first()
    fun setDeveloperMode(enabled: Boolean) = GlobalScope.launch { devicePreferenceDataSource.developerModePref.setValue(enabled) }

    suspend fun getAppInstanceId(): String = devicePreferenceDataSource.appInstanceIdPref.flow.first()

    val lastInstalledVersionCodeFlow: Flow<Int> get()  = devicePreferenceDataSource.lastInstalledVersionCodePref.flow
    suspend fun getLastInstalledVersionCode(): Int = devicePreferenceDataSource.lastInstalledVersionCodePref.flow.first()
    fun setLastInstalledVersionCodeAsync(version: Int) = GlobalScope.launch { devicePreferenceDataSource.lastInstalledVersionCodePref.setValue(version) }

    val rangeFlow: Flow<Int> get()  = devicePreferenceDataSource.rangePref.flow
    suspend fun getRange(): Int = devicePreferenceDataSource.rangePref.flow.first()
    fun setRangeAsync(range: Int) = GlobalScope.launch { devicePreferenceDataSource.rangePref.setValue(range) }

    suspend fun getWorkSchedulerVersion(): Int = devicePreferenceDataSource.workSchedulerVersionPref.flow.first()
    fun setWorkSchedulerVersionAsync(version: Int) = GlobalScope.launch { devicePreferenceDataSource.workSchedulerVersionPref.setValue(version) }

    companion object {
        val RANGE_OPTIONS = listOf(1, 2, 3)
    }
}
