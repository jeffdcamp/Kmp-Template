package org.jdc.kmp.template.analytics

import co.touchlab.kermit.Logger
import isDebugMode
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.hours

/**
 * DebugView for Firebase Analytics (https://firebase.google.com/docs/analytics/debugview)
 * 1. /home/<your username here>/Android/Sdk/platform-tools/adb shell setprop debug.firebase.analytics.app org.lds.ldssa.dev
 * 2. Go to Firebase Console > Analytics > DebugView
 * 3. When finished: /home/<your username here>/Android/Sdk/platform-tools/adb shell setprop debug.firebase.analytics.app .none.
 *
 */
class DefaultAnalytics : Analytics {
    private var lastDimensionUpdate: Instant? = null

    init {
        updateFirebaseUserProperties()
//        AppAnalytics.register(FirebaseStrategy(firebaseAnalytics))

        // Set log levels
        AppAnalytics.setLogLevel(if (isDebugMode()) AppAnalytics.LogLevel.VERBOSE else AppAnalytics.LogLevel.UPLOAD)

        // Test Analytics in Logcat
        if (isDebugMode()) {
            val testStrategy = TestStrategy { Logger.w { "^^^ $it" } }
            AppAnalytics.register(testStrategy)

            // must be done AFTER register() (register() calls setLogLevel())
            testStrategy.setLogLevel(AppAnalytics.LogLevel.VERBOSE)
        }
    }

    override fun upload() {
    }

    override fun setDimensions(dimensions: List<String>) {
    }

    override fun logEvent(eventId: String, attributes: Map<String, String>, scope: AppAnalytics.ScopeLevel) {
        updateDimensions()
        AppAnalytics.logEvent(eventId, attributes, scopeLevel = scope)
    }

    override fun logScreen(screen: String) {
        updateDimensions()
        AppAnalytics.logScreen(screen, scopeLevel = AppAnalytics.ScopeLevel.DEV)
    }

    override fun enableInAppNotifications(allow: Boolean) {
    }

    private fun updateDimensions() {
        val now = Clock.System.now()

        val lastUpdate = lastDimensionUpdate
        if (lastUpdate == null || now > lastUpdate.plus(1.hours)) {
            lastDimensionUpdate = now
            updateFirebaseUserProperties()
        }
    }

    private fun updateFirebaseUserProperties() {
//        firebaseAnalytics?.apply {
////            setUserProperty("xxxUserProperty", myProperty)
//        }
    }
}
