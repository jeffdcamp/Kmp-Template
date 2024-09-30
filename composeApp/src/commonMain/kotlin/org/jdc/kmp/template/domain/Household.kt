package org.jdc.kmp.template.domain

import kotlinx.datetime.Clock
import org.jdc.kmp.template.domain.inline.CreatedTime
import org.jdc.kmp.template.domain.inline.HouseholdId
import org.jdc.kmp.template.domain.inline.LastModifiedTime
import org.jdc.kmp.template.domain.inline.LastName
import randomUUID

data class Household(
    val id: HouseholdId = HouseholdId(randomUUID()),
    val name: LastName,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
)