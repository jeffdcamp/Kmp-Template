package org.jdc.kmp.template.model.db.main.directoryitem

import androidx.room.DatabaseView
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.domain.inline.LastName

@DatabaseView(
    viewName = DirectoryItemEntityView.VIEW_NAME,
    value = DirectoryItemEntityView.VIEW_QUERY
)
data class DirectoryItemEntityView(
    val individualId: IndividualId,
    val firstName: FirstName?,
    val lastName: LastName?
) {
    fun getFullName() = "${firstName?.value.orEmpty()} ${lastName?.value.orEmpty()}"

    companion object {
        const val VIEW_NAME = "DirectoryItem"
        const val VIEW_QUERY = "SELECT id AS individualId, lastName, firstName FROM Individual"
    }
}