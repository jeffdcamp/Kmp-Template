package org.jdc.kmp.template.domain.inline

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class IndividualId(val value: String) {
    init {
        require(value.isNotBlank()) { "IndividualId cannot have a empty value" }
    }
}

data class HouseholdId(val value: String) {
    init {
        require(value.isNotBlank()) { "HouseholdId cannot have a empty value" }
    }
}

data class FirstName(val value: String)

data class LastName(val value: String)

data class Phone(val value: String)

data class Email(val value: String)

data class CreatedTime(val value: Instant)

data class LastModifiedTime(val value: Instant)
