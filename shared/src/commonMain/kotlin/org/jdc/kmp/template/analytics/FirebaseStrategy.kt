@file:Suppress("unused")

package org.jdc.kmp.template.analytics

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import org.dbtools.kmp.commons.analytics.AnalyticError
import org.dbtools.kmp.commons.analytics.AnalyticEvent
import org.dbtools.kmp.commons.analytics.AnalyticScreen
import org.dbtools.kmp.commons.analytics.AppAnalytics
import java.util.Locale

class FirebaseStrategy(
    private val firebaseAnalytics: FirebaseAnalytics
) : AppAnalytics.Strategy {

    private var logLevel = AppAnalytics.LogLevel.NONE

    var eventScopeLevel = AppAnalytics.DEFAULT_EVENT_SCOPE_LEVEL
    var screenScopeLevel = AppAnalytics.DEFAULT_SCREEN_SCOPE_LEVEL

    override fun logEvent(event: AnalyticEvent) {
        if (event.scopeLevel.ordinal > eventScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logEvent(${event.id})")
        consoleLogParameterMap(event.params)

        firebaseAnalytics.logEvent(formatValidName(event.id), createParameterMapBundle(event.params))
    }

    override fun logScreen(screen: AnalyticScreen) {
        if (screen.scopeLevel.ordinal > screenScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logScreen(${screen.screenTitle})")
        consoleLogParameterMap(screen.params)

        val parameters = createParameterMapBundle(screen.params)
        parameters["screen_name"] = screen.screenTitle // FirebaseAnalytics.Param.SCREEN_NAME
        firebaseAnalytics.logEvent("screen_view", parameters) // FirebaseAnalytics.Event.SCREEN_VIEW
    }

    private fun createParameterMapBundle(parameterMap: Map<String, String>?): MutableMap<String, Any> {
        val parameters = mutableMapOf<String, Any>()

        parameterMap?.forEach { param ->
            parameters[formatValidName(param.key)] = param.value
        }

        return parameters
    }

    fun formatValidName(name: String): String {
        return name.trim().lowercase(Locale.getDefault()).replace(invalidCharactersRegex, "_").take(MAX_EVENT_NAME_LENGTH)
    }

    override fun logError(error: AnalyticError) {
        // Not logging errors to Firebase
    }

    override fun setLogLevel(logLevel: AppAnalytics.LogLevel, enableProviderLogging: Boolean) {
        this.logLevel = logLevel
    }

    private fun consoleLogMessage(level: AppAnalytics.LogLevel, message: String) {
        if (level.ordinal <= logLevel.ordinal) {
            Logger.d { message }
        }
    }

    private fun consoleLogParameterMap(parameterMap: Map<String, String>?) {
        if (logLevel.ordinal >= AppAnalytics.LogLevel.VERBOSE.ordinal) {
            parameterMap?.keys?.forEach {
                consoleLogMessage(AppAnalytics.LogLevel.VERBOSE, "  $it:${parameterMap[it]}")
            }
        }
    }

    companion object {
        private const val MAX_EVENT_NAME_LENGTH = 40
        private val invalidCharactersRegex = """(\s+|[^a-z0-9_])""".toRegex()
    }
}