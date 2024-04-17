package org.jdc.kmp.template.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import org.jdc.kmp.template.resources.strings.LocalStringResources
import org.jdc.kmp.template.resources.strings.StringResources
import java.util.Locale

object Resources {
    val strings: StringResources
        @Composable
        @ReadOnlyComposable
        get() = LocalStringResources.current

    fun getStringResources(): StringResources {
        return StringResources.getStrings(Locale.getDefault().language)
    }
}