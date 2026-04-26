package org.jdc.kmp.template.model.db.main.household

import androidx.room3.ColumnInfo
import androidx.room3.Relation
import org.jdc.kmp.template.model.db.main.individual.IndividualEntity

data class HouseholdMembers(
    var id: String = "",
    @ColumnInfo(name = "name")
    var householdName: String = "",

    @Relation(parentColumn = "id", entityColumn = "householdId", entity = IndividualEntity::class, projection = ["firstName"])
    var memberNames: List<String> = emptyList()
)
