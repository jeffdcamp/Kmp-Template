import platform.UIKit.UIDevice
import platform.Foundation.NSUUID

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual fun randomUUID(): String = NSUUID().UUIDString()
