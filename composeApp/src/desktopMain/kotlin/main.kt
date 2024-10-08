import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jdc.kmp.template.App
import org.jdc.kmp.template.inject.KoinInitializer

fun main() = application {
    KoinInitializer().init()

    Window(onCloseRequest = ::exitApplication, title = "Kmp-Template") {
        App(Locale.current.language)
    }
}
