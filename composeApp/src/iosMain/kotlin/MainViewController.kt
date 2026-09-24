import androidx.compose.ui.window.ComposeUIViewController
import org.jdc.kmp.template.ComposeApp
import org.jdc.kmp.template.inject.getAllKoinModules
import org.koin.mp.KoinPlatform
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

@Suppress("FunctionName", "unused") // Called from Swift (ContentView.swift)
fun MainViewController(): UIViewController {
    if (KoinPlatform.getKoinOrNull() == null) {
        startKoin {
            modules(getAllKoinModules())
        }
    }
    return ComposeUIViewController { ComposeApp() }
}
