package org.jdc.kmp.template.model.db.main.migration

import okio.Path
import okio.Path.Companion.toPath

object DatabaseTestUtil {
    // Paths should use kotlinx-io in the future (https://issuetracker.google.com/issues/330588883)
    val SCHEMA_DIRECTORY_PATH: Path = "schemas".toPath()
    fun getDatabasePath(databaseName: String): Path = "build/test/db/$databaseName".toPath()
}