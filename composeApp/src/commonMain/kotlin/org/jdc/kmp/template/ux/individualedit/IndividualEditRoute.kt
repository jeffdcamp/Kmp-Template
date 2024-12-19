package org.jdc.kmp.template.ux.individualedit


import kotlinx.serialization.Serializable
import org.dbtools.kmp.commons.compose.navigation.NavigationRoute
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.ui.navigation.NavTypeMaps
import kotlin.reflect.typeOf

@Serializable
data class IndividualEditRoute(
    val individualId: IndividualId? = null
): NavigationRoute

fun IndividualEditRoute.Companion.typeMap() = mapOf(
    typeOf<IndividualId?>() to NavTypeMaps.IndividualIdNullableNavType,
)
