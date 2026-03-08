package org.jdc.kmp.template.ux.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import org.dbtools.kmp.commons.compose.dialog.HandleDialogUiState
import org.dbtools.kmp.commons.compose.navigation3.navigator.Navigation3Navigator
import org.jdc.kmp.template.SharedResources
import org.jdc.kmp.template.ui.compose.setting.Setting
import org.jdc.kmp.template.ux.MainAppScaffoldWithNavBar

@Composable
fun SettingsScreen(
    navigator: Navigation3Navigator,
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiStateFlow.collectAsState()

    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = stringResource(SharedResources.strings.settings),
        hideNavigation = true,
        onNavigationClick = { navigator.pop() },
    ) {
        when (val uiState = uiState) {
            SettingsUiState.Loading -> {}
            is SettingsUiState.Ready -> {
                SettingsContent(
                    uiState = uiState,
                    onThemeSettingClick = { viewModel.onThemeSettingClick() },
                    onLastInstalledVersionCodeClick = { viewModel.onLastInstalledVersionCodeClick() },
                    setSortByLastName = { viewModel.setSortByLastName(it) },
                    setDynamicTheme = { viewModel.setDynamicTheme(it) },
                    onRangeClick = { viewModel.onRangeClick() }
                )
            }
        }
    }

    HandleDialogUiState(viewModel.dialogUiStateFlow)
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState.Ready,
    onThemeSettingClick: () -> Unit,
    onLastInstalledVersionCodeClick: () -> Unit,
    setSortByLastName: (Boolean) -> Unit,
    setDynamicTheme: (Boolean) -> Unit,
    onRangeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier.verticalScroll(scrollState)
    ) {
        Setting.Header(stringResource(SharedResources.strings.display))
        Setting.Clickable(stringResource(SharedResources.strings.theme), uiState.currentThemeTitle) { onThemeSettingClick() }
        if (uiState.showDynamicTheme) {
            Setting.Switch(stringResource(SharedResources.strings.dynamic_theme), checked = uiState.dynamicTheme) { setDynamicTheme(it) }
        }
        Setting.Switch(stringResource(SharedResources.strings.sort_by_last_name), checked = uiState.sortByLastName) { setSortByLastName(it) }

        // not translated because this should not be visible for release builds
        Setting.Header("Developer Options")
        Setting.Clickable(text = "Last Installed Version Code", uiState.currentLastInstalledVersionCode) { onLastInstalledVersionCodeClick() }
        Setting.Clickable(text = "Range", uiState.range) { onRangeClick() }
    }
}
