package org.jdc.kmp.template.ux

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.dbtools.kmp.commons.compose.navigation3.navigator.Navigation3Navigator
import org.jdc.kmp.template.SharedResources

@Composable
fun MainAppScaffoldWithNavBar(
    navigator: Navigation3Navigator,
    title: String,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    MainAppScaffoldWithNavBar(
        navigator = navigator,
        title = { Text(text = title) },
        modifier = modifier,
        navigationIconVisible = navigationIconVisible,
        navigationIcon = navigationIcon,
        onNavigationClick = onNavigationClick,
        hideNavigation = hideNavigation,
        actions = actions,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = contentWindowInsets,
        content = content
    )
}

@Composable
fun MainAppScaffoldWithNavBar(
    navigator: Navigation3Navigator,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    hideNavigation: Boolean = false,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    when {
        hideNavigation -> {
            AppScaffold(
                modifier = modifier,
                title = title,
                navigationIconVisible = navigationIconVisible,
                navigationIcon = navigationIcon,
                onNavigationClick = onNavigationClick,
                actions = actions,
                floatingActionButton = floatingActionButton,
                content = content
            )
        }
        else -> {
            val windowSize = currentWindowSize()
            NavigationSuiteScaffold(
                layoutType = getNavigationSuiteType(windowSize.toDpSize()),
                navigationSuiteItems = {
                    NavBarItem.entries.forEach { navBarItem: NavBarItem ->
                        val isSelected = navBarItem.route == navigator.getSelectedTopLevelRoute()
                        val imageVector = if (isSelected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector
                        item(
                            selected = isSelected,
                            icon = { Icon(imageVector = imageVector, contentDescription = null) },
                            label = { stringResource(navBarItem.label) },
                            onClick = { navigator.navigateTopLevel(navBarItem.route, reselected = isSelected) },
                        )
                    }
                }
            ) {
                AppScaffold(
                    modifier = modifier,
                    title = title,
                    navigationIconVisible = navigationIconVisible,
                    navigationIcon = navigationIcon,
                    onNavigationClick = onNavigationClick,
                    actions = actions,
                    floatingActionButton = floatingActionButton,
                    floatingActionButtonPosition = floatingActionButtonPosition,
                    contentWindowInsets = contentWindowInsets,
                    content = content
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppScaffold(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconVisible: Boolean = true,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = WindowInsets(0, 0, 0, 0), // required when using enableEdgeToEdge
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val appScaffoldModifier = modifier
        .nestedScroll(scrollBehavior.nestedScrollConnection)
        .imePadding()

    // TopAppBar
    val topAppBar: @Composable (() -> Unit) = {
        TopAppBar(
            title = title,
            navigationIcon = if (!navigationIconVisible) {
                {}
            } else {
                {
                    IconButton(onClick = { onNavigationClick?.invoke() }) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = stringResource(SharedResources.strings.back),
                            modifier = Modifier
                        )
                    }
                }
            },
            actions = { actions?.invoke(this) },
            scrollBehavior = scrollBehavior
        )
    }

    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = contentWindowInsets,
        modifier = appScaffoldModifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

private fun getNavigationSuiteType(windowSize: DpSize): NavigationSuiteType {
    // NavigationSuiteScaffoldDefaults.navigationSuiteType(currentWindowAdaptiveInfo())
    return if (windowSize.width >= 600.dp) {
        NavigationSuiteType.NavigationRail
    } else {
        NavigationSuiteType.NavigationBar
    }
}

@Composable
private fun IntSize.toDpSize(): DpSize = with(LocalDensity.current) {
    DpSize(width.toDp(), height.toDp())
}
