
import androidx.room.Room
import androidx.room.RoomDatabase
import co.touchlab.kermit.Logger
import org.jdc.kmp.template.model.db.main.MainDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val databaseModule = module {
    single<RoomDatabase.Builder<MainDatabase>> {
        Logger.e { "+++ databaseModule" }

        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath(MainDatabase.DATABASE_NAME)
        Room.databaseBuilder<MainDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}
