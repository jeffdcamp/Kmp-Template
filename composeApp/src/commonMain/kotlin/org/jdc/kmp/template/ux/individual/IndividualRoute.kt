package org.jdc.kmp.template.ux.individual

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.dbtools.kmp.commons.compose.navigation3.deeplink.RouteMatcher
import org.dbtools.kmp.commons.network.ktor.Uri
import org.dbtools.kmp.commons.network.ktor.toUri
import org.jdc.kmp.template.domain.inline.IndividualId

@Serializable
data class IndividualRoute(
    val individualId: IndividualId
): NavKey

object IndividualRouteMatcher : RouteMatcher<IndividualRoute>("/individual".toUri()) {
    override fun matchesKey(route: NavKey) = route is IndividualRoute

    override fun parse(uri: Uri): IndividualRoute? {
        uri.pathSegments
        return IndividualRoute(
            individualId = uri.pathSegments.getOrNull(1)?.let { IndividualId(it) } ?: return null
        )
    }

    override fun toUri(route: IndividualRoute): String {
        return baseUri.newBuilder()
            .appendPath(route.individualId.value)
            .build().toString()
    }
}