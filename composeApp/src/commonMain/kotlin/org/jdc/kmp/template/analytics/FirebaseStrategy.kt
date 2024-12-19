@file:Suppress("unused")

package org.jdc.kmp.template.analytics

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import java.util.Locale

class FirebaseStrategy(
    private val firebaseAnalytics: FirebaseAnalytics
) : AppAnalytics.Strategy {

    private var logLevel = AppAnalytics.LogLevel.NONE

    var eventScopeLevel = AppAnalytics.DEFAULT_EVENT_SCOPE_LEVEL
    var screenScopeLevel = AppAnalytics.DEFAULT_SCREEN_SCOPE_LEVEL

    override fun logEvent(eventId: String, parameterMap: Map<String, String>, scopeLevel: AppAnalytics.ScopeLevel) {
        if (scopeLevel.ordinal > eventScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logEvent($eventId)")
        consoleLogParameterMap(parameterMap)

        firebaseAnalytics.logEvent(formatValidName(eventId), createParameterMapBundle(parameterMap))
    }

    /**
     * Log screens
     * https://firebase.googleblog.com/2020/08/google-analytics-manual-screen-view.html
     */
    override fun logScreen(screenTitle: String, parameterMap: Map<String, String>, scopeLevel: AppAnalytics.ScopeLevel) {
        if (scopeLevel.ordinal > screenScopeLevel.ordinal) {
            return
        }

        consoleLogMessage(AppAnalytics.LogLevel.EVENT, "logScreen($screenTitle)")
        consoleLogParameterMap(parameterMap)

        val parameters = createParameterMapBundle(parameterMap)
        parameters["screen_name"] = screenTitle // FirebaseAnalytics.Param.SCREEN_NAME
        firebaseAnalytics.logEvent("screen_view", parameters) // FirebaseAnalytics.Event.SCREEN_VIEW
    }

    private fun createParameterMapBundle(parameterMap: Map<String, String>): MutableMap<String, Any> {
        val parameters = mutableMapOf<String, Any>()

        parameterMap.forEach { param ->
            parameters[formatValidName(param.key)] = param.value
        }

        return parameters
    }

    fun formatValidName(name: String): String {
        // make sure the name is a valid event name
        // https://firebase.google.com/docs/reference/cpp/group/event-names
        return name.trim().lowercase(Locale.getDefault()).replace(invalidCharactersRegex, "_").take(MAX_EVENT_NAME_LENGTH)
    }

    override fun logError(errorMessage: String, errorClass: String, scopeLevel: AppAnalytics.ScopeLevel) {
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

    private fun consoleLogParameterMap(parameterMap: Map<String, String>) {
        if (logLevel.ordinal >= AppAnalytics.LogLevel.VERBOSE.ordinal) {
            parameterMap.keys.forEach {
                consoleLogMessage(AppAnalytics.LogLevel.VERBOSE, "  $it:${parameterMap[it]}")
            }
        }
    }

    companion object {
        private const val MAX_EVENT_NAME_LENGTH = 40
        private val invalidCharactersRegex = """(\s+|[^a-z0-9_])""".toRegex()
    }
}