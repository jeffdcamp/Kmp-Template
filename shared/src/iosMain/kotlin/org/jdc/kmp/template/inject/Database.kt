fun getMainDatabaseBuilder(): RoomDatabase.Builder<MainDatabase> {
    val dbFilePath = NSHomeDirectory() + "/${MainDatabase.DATABASE_NAME}"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory =  { MainDatabase::class.instantiateImpl() }
    )
}