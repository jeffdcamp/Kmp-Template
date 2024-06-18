package org.jdc.kmp.template.inject

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.currentKoinScope

/**
 * Temp workaround/replacement for koinInject() for ViewModels that need to be lifecycle/scope aware
 * (you don't want to re-create the ViewModel on configuration change)
 */
@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}