package org.jdc.kmp.template.ui.compose.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

object Setting {
    @Composable
    fun Header(text: String) {
        Text(
            text,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }

    @Composable
    fun Switch(
        text: String,
        modifier: Modifier = Modifier,
        checked: Boolean = false,
        secondaryText: String? = null,
        icon: @Composable (() -> Unit)? = null,
        onClickBody: ((Boolean) -> Unit)? = null
    ) {
        ListItem(
            modifier = modifier
                .toggleable(checked, onValueChange = { onClickBody?.invoke(it) }, role = Role.Switch),
            leadingContent = icon,
            headlineContent = {
                Text(text)
            },
            supportingContent = if (!secondaryText.isNullOrBlank()) {
                { Text(secondaryText) }
            } else {
                null
            },
            trailingContent = {
                Switch(
                    checked = checked,
                    onCheckedChange = onClickBody
                )
            }
        )
    }

    @Composable
    fun Clickable(
        text: String,
        secondaryText: String? = null,
        icon: @Composable (() -> Unit)? = null,
        onClickBody: (() -> Unit)? = null
    ) {
        ListItem(
            modifier = Modifier
                .clickable { onClickBody?.invoke() },
            leadingContent = icon,
            headlineContent = {
                Text(text)
            },
            supportingContent = if (!secondaryText.isNullOrBlank()) {
                { Text(secondaryText) }
            } else {
                null
            }
        )
    }
}
