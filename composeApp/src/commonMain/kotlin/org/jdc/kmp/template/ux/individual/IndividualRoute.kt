package org.jdc.kmp.template.ux.individual


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.jdc.kmp.template.domain.inline.IndividualId

@Serializable
data class IndividualRoute(
    val individualId: IndividualId
): NavKey
