package org.jdc.kmp.template.domain

import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.kmp.template.domain.inline.CreatedTime
import org.jdc.kmp.template.domain.inline.Email
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.HouseholdId
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.domain.inline.LastModifiedTime
import org.jdc.kmp.template.domain.inline.LastName
import org.jdc.kmp.template.domain.inline.Phone
import org.jdc.kmp.template.domain.type.IndividualType
import kotlin.uuid.Uuid

data class Individual(
    val id: IndividualId = IndividualId(Uuid.random().toString()),
    val householdId: HouseholdId? = null,

    val individualType: IndividualType = IndividualType.HEAD,
    val firstName: FirstName? = null,
    val lastName: LastName? = null,
    val birthDate: LocalDate? = null,
    val alarmTime: LocalTime? = null,
    val phone: Phone? = null,
    val email: Email? = null,
    val available: Boolean = false,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
) {
    fun getFullName(): String = "${firstName?.value.orEmpty()} ${lastName?.value.orEmpty()}"
}
