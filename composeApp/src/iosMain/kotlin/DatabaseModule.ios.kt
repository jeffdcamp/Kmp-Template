import org.jdc.kmp.template.model.db.getMainDatabase
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.dsl.module

actual val databaseModule = module {
    single<MainDatabase> { getMainDatabase(getMainDatabaseBuilder()) }
}

private fun getMainDatabaseBuilder(): RoomDatabase.Builder<MainDatabase> {
    val dbFilePath = NSHomeDirectory() + "/${MainDatabase.DATABASE_NAME}"
    return Room.databaseBuilder<MainDatabase>(
        name = dbFilePath,
        factory =  { MainDatabase::class.instantiateImpl() }
    )
}
