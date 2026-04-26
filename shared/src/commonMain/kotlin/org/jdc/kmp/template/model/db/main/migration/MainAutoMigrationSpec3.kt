package org.jdc.kmp.template.model.db.main.migration

import androidx.room3.RenameColumn
import androidx.room3.migration.AutoMigrationSpec

@RenameColumn(tableName = "Individual", fromColumnName = "availabley", toColumnName = "available")
class MainAutoMigrationSpec3 : AutoMigrationSpec