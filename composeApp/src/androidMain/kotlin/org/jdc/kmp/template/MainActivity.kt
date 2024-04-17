package org.jdc.kmp.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.intl.Locale
import co.touchlab.kermit.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentConfiguration = LocalConfiguration.current // trigger re-composition on configuration change (language, etc)

            Logger.e { "===== Language (ISO 639) =====" }
            Logger.e { "currentConfiguration: ${currentConfiguration.locales.get(0)}" }
            Logger.e { "Locale.current.language: ${Locale.current.language}" }
            Logger.e { "java.util.Locale.getDefault(): ${java.util.Locale.getDefault()}" }
            Logger.e { "java.util.Locale.getDefault().language: ${java.util.Locale.getDefault().language}" }
            Logger.e { "java.util.Locale.getDefault().isO3Language: ${java.util.Locale.getDefault().isO3Language}" }
            Logger.e { "===== BCP 47 =====" }
            Logger.e { "currentConfiguration.toLanguageTag(): ${currentConfiguration.locales.get(0).toLanguageTag()}" }
            Logger.e { "Locale.current.language: ${Locale.current.toLanguageTag()}" }
            Logger.e { "java.util.Locale.getDefault(): ${java.util.Locale.getDefault()}" }
            Logger.e { "java.util.Locale.getDefault().toLanguageTag(): ${java.util.Locale.getDefault().toLanguageTag()}" }

            App(Locale.current.language)
        }
    }
}
