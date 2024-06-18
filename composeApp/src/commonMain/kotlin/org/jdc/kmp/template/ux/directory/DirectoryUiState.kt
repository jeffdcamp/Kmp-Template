package org.jdc.kmp.template.ux.directory

data class DirectoryUiState(
    // Data
//    val directoryListFlow: StateFlow<List<DirectoryItemEntityView>> = MutableStateFlow(emptyList()),

    // Events
    val onNewClicked: () -> Unit = {},
//    val onIndividualClicked: (individualId: IndividualId) -> Unit = {},
    val onSettingsClicked: () -> Unit = {}
)