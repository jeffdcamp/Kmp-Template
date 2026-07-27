package org.jdc.kmp.template.analytics

import co.touchlab.kermit.Logger
import isDebugMode
import org.dbtools.kmp.commons.analytics.AnalyticError
import org.dbtools.kmp.commons.analytics.AnalyticEvent
import org.dbtools.kmp.commons.analytics.AnalyticScreen
import org.dbtools.kmp.commons.analytics.AppAnalytics
import org.dbtools.kmp.commons.analytics.TestStrategy
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

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
        platformAnalyticsStrategy()?.let { AppAnalytics.register(it) }
//        AppAnalytics.register(FirebaseStrategy(firebaseAnalytics))

        // Set log levels
        AppAnalytics.setLogLevel(if (isDebugMode()) AppAnalytics.LogLevel.VERBOSE else AppAnalytics.LogLevel.UPLOAD)

        // Test Analytics in Logcat
        if (isDebugMode() && AppAnalytics.findRegistered<TestStrategy>().isEmpty()) {
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

    override fun logEvent(event: AnalyticEvent) {
        updateDimensions()
        AppAnalytics.logEvent(event)
    }

    override fun logScreen(screen: AnalyticScreen) {
        updateDimensions()
        AppAnalytics.logScreen(screen)
    }

    override fun logError(error: AnalyticError) {
        updateDimensions()
        AppAnalytics.logError(error)
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
