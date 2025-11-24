@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)

package org.jdc.kmp.template.ux

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import org.jdc.kmp.template.ux.directory.DirectoryRoute

@Composable
fun MainAppScaffoldWithNavBar(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffoldWithNavBar(
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
//    val activity = LocalContext.current.requireActivity()
//    val viewModel: MainViewModel = hiltViewModel(activity)

    var selectedBarItem by remember { mutableStateOf(NavBarItem.PEOPLE) }
//    val selectedBarItem by viewModel.selectedNavBarFlow.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//    val windowSize = currentWindowSize()

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
                            contentDescription = "Back",
                            modifier = Modifier
                        )
                    }
                }
            },
            actions = { actions?.invoke(this) },
            scrollBehavior = scrollBehavior
        )
    }

    when {
        hideNavigation -> {
            AppScaffold(
                topAppBar = topAppBar,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                contentWindowInsets = contentWindowInsets,
                modifier = modifier,
                scrollBehavior = scrollBehavior,
                content = content
            )
        }

        else -> {
            NavigationSuiteScaffold(
                layoutType =  NavigationSuiteType.NavigationBar,
                navigationSuiteItems = {
                    NavBarItem.entries.forEach { navBarItem ->
                        val selected = selectedBarItem == navBarItem
                        val imageVector = if (selected) navBarItem.selectedImageVector else navBarItem.unselectedImageVector

                        item(
                            selected = selected,
                            icon = { Icon(imageVector = imageVector, contentDescription = null) },
                            label = run {
                                { Text(text = navBarItem.text, maxLines = 1) }
                            },
                            onClick = { selectedBarItem = navBarItem }
                        )
                    }
                },
            ) {
                AppScaffold(
                    topAppBar = topAppBar,
                    floatingActionButton = floatingActionButton,
                    floatingActionButtonPosition = floatingActionButtonPosition,
                    contentWindowInsets = contentWindowInsets,
                    modifier = modifier,
                    scrollBehavior = scrollBehavior,
                    content = content
                )
            }
        }
    }
}

@Composable
private fun AppScaffold(
    topAppBar: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit,
    floatingActionButtonPosition: FabPosition,
    contentWindowInsets: WindowInsets,
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        contentWindowInsets = contentWindowInsets,
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding(),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

/**
 * Per <a href="https://m3.material.io/components/navigation-drawer/guidelines">Material Design 3 guidelines</a>,
 * the selection of the appropriate navigation component should be contingent on the available
 * window size:
 * - Bottom Bar for compact window sizes (below 600dp)
 * - Navigation Rail for medium and expanded window sizes up to 1240dp (between 600dp and 1240dp)
 * - Navigation Drawer to window size above 1240dp
 */
fun getNavigationSuiteType(windowSize: DpSize): NavigationSuiteType {
    return if (windowSize.width > 1240.dp) {
        NavigationSuiteType.NavigationDrawer
    } else if (windowSize.width >= 600.dp) {
        NavigationSuiteType.NavigationRail
    } else {
        NavigationSuiteType.NavigationBar
    }
}


enum class NavBarItem(
    val unselectedImageVector: ImageVector,
    val selectedImageVector: ImageVector,
    val route: NavKey,
    val text: String
) {
    PEOPLE(Icons.Outlined.People, Icons.Filled.People, DirectoryRoute,"People"),
    ABOUT(Icons.Outlined.Info, Icons.Filled.Info,  DirectoryRoute,"About");
}
