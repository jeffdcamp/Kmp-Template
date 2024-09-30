import androidx.room.Room
import androidx.room.RoomDatabase
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.dsl.module
import java.io.File

actual val databaseModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), MainDatabase.DATABASE_NAME)
        println("DB File: ${dbFile.absolutePath}")
//        val dbFile = File("/home/jeffdcamp/home/Download", MainDatabase.DATABASE_NAME)
        Room.databaseBuilder<MainDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
