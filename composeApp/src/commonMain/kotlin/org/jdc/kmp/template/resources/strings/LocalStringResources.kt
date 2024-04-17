package org.jdc.kmp.template.resources.strings

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalStringResources: ProvidableCompositionLocal<StringResources> =
    staticCompositionLocalOf {
        StringResources.getStrings() // fallback to default language
    }
