
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jdc.kmp.template.ComposeApp
import org.jdc.kmp.template.inject.getAllKoinModules
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    startKoin {
        modules(getAllKoinModules())
    }
    Window(onCloseRequest = ::exitApplication, title = "Kmp-Template") {
        ComposeApp()
    }
}
