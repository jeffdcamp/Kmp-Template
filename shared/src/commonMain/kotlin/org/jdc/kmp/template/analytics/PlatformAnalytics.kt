package org.jdc.kmp.template.analytics

import org.dbtools.kmp.commons.analytics.AppAnalytics

expect fun platformAnalyticsStrategy(): AppAnalytics.Strategy?
