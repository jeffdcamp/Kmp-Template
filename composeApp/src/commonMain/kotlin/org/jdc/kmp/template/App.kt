package org.jdc.kmp.template

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.jdc.kmp.template.resources.strings.LocalStringResources
import org.jdc.kmp.template.resources.strings.StringResources
import org.jdc.kmp.template.ux.directory.DirectoryScreen

@Composable
fun App(
    locale: String,
    stringResources: StringResources = StringResources.getStrings(locale)
) {
    CompositionLocalProvider(
        LocalStringResources provides stringResources
    ) {
        MaterialTheme {
            DirectoryScreen()
        }
    }
}