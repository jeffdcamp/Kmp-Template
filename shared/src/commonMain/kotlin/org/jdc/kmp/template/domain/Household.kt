package org.jdc.kmp.template.domain

import kotlin.time.Clock
import org.jdc.kmp.template.domain.inline.CreatedTime
import org.jdc.kmp.template.domain.inline.HouseholdId
import org.jdc.kmp.template.domain.inline.LastModifiedTime
import org.jdc.kmp.template.domain.inline.LastName
import kotlin.uuid.Uuid

data class Household(
    val id: HouseholdId = HouseholdId(Uuid.random().toString()),
    val name: LastName,

    val created: CreatedTime = CreatedTime(Clock.System.now()),
    val lastModified: LastModifiedTime = LastModifiedTime(Clock.System.now())
)