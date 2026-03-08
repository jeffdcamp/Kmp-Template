import android.os.Build

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun isDebugMode(): Boolean = true

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}
