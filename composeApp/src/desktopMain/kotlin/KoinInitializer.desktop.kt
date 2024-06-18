import org.jdc.kmp.template.inject.appModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(
                viewModelModule,
                appModule
            )
        }
    }
}