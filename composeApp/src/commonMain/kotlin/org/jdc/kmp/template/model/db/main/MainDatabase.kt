package org.jdc.kmp.template.model.db.main

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.jdc.kmp.template.model.db.KotlinDateTimeTextConverter
import org.jdc.kmp.template.model.db.converter.DataValueClassTypeConverters
import org.jdc.kmp.template.model.db.main.directoryitem.DirectoryItemDao
import org.jdc.kmp.template.model.db.main.directoryitem.DirectoryItemEntityView
import org.jdc.kmp.template.model.db.main.household.HouseholdDao
import org.jdc.kmp.template.model.db.main.household.HouseholdEntity
import org.jdc.kmp.template.model.db.main.individual.IndividualDao
import org.jdc.kmp.template.model.db.main.individual.IndividualEntity

@Database(
    entities = [
        IndividualEntity::class,
        HouseholdEntity::class
    ],
    views = [
        DirectoryItemEntityView::class
    ],
    version = 1
)
@ConstructedBy(MainDatabaseConstructor::class)
@TypeConverters(KotlinDateTimeTextConverter::class, DataValueClassTypeConverters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun individualDao(): IndividualDao
    abstract fun householdDao(): HouseholdDao
    abstract fun directoryItemDao(): DirectoryItemDao

    companion object {
        const val DATABASE_NAME = "main.db"
//        val DATABASE_VIEW_QUERIES = listOf(
//            DatabaseViewQuery(DirectoryItemEntityView.VIEW_NAME, DirectoryItemEntityView.VIEW_QUERY)
//        )

        fun getMainDatabase(builder: RoomDatabase.Builder<MainDatabase>): MainDatabase {
            return builder
                .addMigrations(object : Migration(1, 2) {
                    override fun migrate(connection: SQLiteConnection) {
                        super.migrate(connection)
                    }
                })
//        .addMigrations(MIGRATIONS)
//        .fallbackToDestructiveMigrationOnDowngrade(true)
                .setDriver(BundledSQLiteDriver())
//                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object MainDatabaseConstructor : RoomDatabaseConstructor<MainDatabase>
