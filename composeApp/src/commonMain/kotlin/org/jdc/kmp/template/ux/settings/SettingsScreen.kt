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
import org.dbtools.kmp.commons.compose.setting.Setting
import org.dbtools.kmp.commons.compose.setting.SettingsUiUtil
import org.jdc.kmp.template.SharedResources
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
        topAppBarColors = SettingsUiUtil.topAppBarColors(),
        scaffoldContainerColor = SettingsUiUtil.scaffoldContainerColor(),
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
        Setting.Group(headerText = stringResource(SharedResources.strings.display)) {
            GroupedClickable(position = Setting.GroupPositionType.FIRST, text = stringResource(SharedResources.strings.theme), secondaryText = uiState.currentThemeTitle) { onThemeSettingClick() }
            if (uiState.showDynamicTheme) {
                GroupedSwitch(position = Setting.GroupPositionType.MIDDLE, text = stringResource(SharedResources.strings.dynamic_theme), selected = uiState.dynamicTheme) { setDynamicTheme(it) }
            }
            GroupedSwitch(position = Setting.GroupPositionType.LAST, text = stringResource(SharedResources.strings.sort_by_last_name), selected = uiState.sortByLastName) { setSortByLastName(it) }
        }

        // not translated because this should not be visible for release builds
        Setting.Group(headerText = "Developer Options") {
            GroupedClickable(
                position = Setting.GroupPositionType.FIRST,
                text = "Last Installed Version Code",
                secondaryText = uiState.currentLastInstalledVersionCode
            ) { onLastInstalledVersionCodeClick() }

            GroupedClickable(position = Setting.GroupPositionType.LAST, text = "Range", secondaryText = uiState.range) { onRangeClick() }
        }
    }
}
