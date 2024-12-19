package org.jdc.kmp.template

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.jdc.kmp.template.ux.NavGraph
import org.koin.compose.KoinContext

@Composable
fun ComposeApp() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            NavGraph(navController)
//                HandleNavBarNavigation(viewModel, navController)
        }
    }
}
