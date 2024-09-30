package org.jdc.kmp.template.model.db.main.household

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import org.jdc.kmp.template.domain.inline.HouseholdId
import org.jdc.kmp.template.domain.inline.LastName

@Entity("Household")
data class HouseholdEntity(
    @PrimaryKey
    val id: HouseholdId,
    val name: LastName,

    val created: Instant,
    val lastModified: Instant
)
