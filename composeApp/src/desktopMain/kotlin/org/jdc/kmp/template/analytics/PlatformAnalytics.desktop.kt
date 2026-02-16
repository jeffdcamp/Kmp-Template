package org.jdc.kmp.template.analytics

import co.touchlab.kermit.Logger

actual fun platformAnalyticsStrategy(): AppAnalytics.Strategy? {
    return TestStrategy { message ->
        Logger.i { "[DesktopAnalytics] $message" }
    }
}
