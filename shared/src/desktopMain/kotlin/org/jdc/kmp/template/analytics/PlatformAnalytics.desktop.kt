package org.jdc.kmp.template.analytics

import co.touchlab.kermit.Logger
import org.dbtools.kmp.commons.analytics.AppAnalytics
import org.dbtools.kmp.commons.analytics.TestStrategy

actual fun platformAnalyticsStrategy(): AppAnalytics.Strategy? {
    return TestStrategy { message ->
        Logger.i { "[DesktopAnalytics] $message" }
    }
}
