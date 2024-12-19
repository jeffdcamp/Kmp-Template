package org.jdc.kmp.template.ux.directory

import kotlinx.coroutines.flow.StateFlow
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.model.db.main.directoryitem.DirectoryItemEntityView

data class DirectoryUiState(
    // Data
    val directoryListFlow: StateFlow<List<DirectoryItemEntityView>>,

    // Events
    val onNewClick: () -> Unit,
    val onIndividualClick: (individualId: IndividualId) -> Unit,
    val onSettingsClick: () -> Unit
)