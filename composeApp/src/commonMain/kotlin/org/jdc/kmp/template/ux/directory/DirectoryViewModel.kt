package org.jdc.kmp.template.ux.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch

class DirectoryViewModel(
    getDirectoryUiStateUseCase: GetDirectoryUiStateUseCase
) : ViewModel() {
    val uiState: DirectoryUiState = getDirectoryUiStateUseCase(viewModelScope)// { navigate(it) }

//    val uiState: DirectoryUiState = DirectoryUiState(
//        onNewClick = {
//            Logger.e { "Hello World!" }
//        }
//    )
}

//public inline fun <reified VM : ViewModel> koinViewModelFactory(
//    scope: Scope,
//    qualifier: Qualifier? = null,
//    noinline parameters: ParametersDefinition? = null,
//): ViewModelFactory<VM> =
//    koinViewModelFactory(
//        viewModelClass = VM::class,
//        scope = scope,
//        qualifier = qualifier,
//        parameters = parameters,
//    )