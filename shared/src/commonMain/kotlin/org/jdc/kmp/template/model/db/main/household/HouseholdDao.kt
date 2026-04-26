package org.jdc.kmp.template.model.db.main.household

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface HouseholdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(householdEntity: HouseholdEntity): Long

    @Query("DELETE FROM Household")
    suspend fun deleteAll()

    // todo Room Issue: recursiveFetchArrayMap not found for Desktop
//    @Query("SELECT * FROM Household")
//    suspend fun findAllMembers(): List<HouseholdMembers>
}
