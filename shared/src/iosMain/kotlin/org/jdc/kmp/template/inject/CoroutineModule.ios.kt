
import kotlinx.coroutines.Dispatchers
import org.jdc.kmp.template.inject.AppCoroutineDispatchers
import org.koin.dsl.module

actual val coroutineModule = module {
    single<AppCoroutineDispatchers> {
        AppCoroutineDispatchers(
            default = Dispatchers.Default,
            io = Dispatchers.Default,
            main = Dispatchers.Main,
        )
    }
}
