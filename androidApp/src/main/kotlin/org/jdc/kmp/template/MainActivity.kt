package org.jdc.kmp.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import co.touchlab.kermit.Logger
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentConfiguration =
                LocalConfiguration.current // trigger re-composition on configuration change (language, etc)

            Logger.e { "===== Language (ISO 639) =====" }
            Logger.e { "currentConfiguration: ${currentConfiguration.locales.get(0)}" }
            Logger.e { "Locale.getDefault().language: ${Locale.getDefault().language}" }
            Logger.e { "java.util.Locale.getDefault(): ${Locale.getDefault()}" }
            Logger.e { "java.util.Locale.getDefault().language: ${Locale.getDefault().language}" }
            Logger.e { "java.util.Locale.getDefault().isO3Language: ${Locale.getDefault().isO3Language}" }
            Logger.e { "===== BCP 47 =====" }
            Logger.e {
                "currentConfiguration.toLanguageTag(): ${
                    currentConfiguration.locales.get(0).toLanguageTag()
                }"
            }
            Logger.e { "Locale.getDefault().toLanguageTag(): ${Locale.getDefault().toLanguageTag()}" }
            Logger.e { "java.util.Locale.getDefault(): ${Locale.getDefault()}" }
            Logger.e {
                "java.util.Locale.getDefault().toLanguageTag(): ${
                    Locale.getDefault().toLanguageTag()
                }"
            }

            ComposeApp()
        }
    }
}
