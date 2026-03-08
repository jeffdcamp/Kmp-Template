package org.jdc.kmp.template.model.db.main.individual

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jdc.kmp.template.domain.inline.Email
import org.jdc.kmp.template.domain.inline.FirstName
import org.jdc.kmp.template.domain.inline.HouseholdId
import org.jdc.kmp.template.domain.inline.IndividualId
import org.jdc.kmp.template.domain.inline.LastName
import org.jdc.kmp.template.domain.inline.Phone
import org.jdc.kmp.template.domain.type.IndividualType

@Entity("Individual")
data class IndividualEntity(
    @PrimaryKey
    val id: IndividualId,
    val householdId: HouseholdId?,
    val individualType: IndividualType,
    val firstName: FirstName?,
    val lastName: LastName?,
    val birthDate: LocalDate?,
    val alarmTime: LocalTime?,
    val phone: Phone?,
    val email: Email?,
    val available: Boolean,

    val created: Instant,
    val lastModified: Instant
)
