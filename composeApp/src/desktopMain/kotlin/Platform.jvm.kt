actual fun getPlatform(): Platform = JVMPlatform()
actual fun isDebugMode(): Boolean = true

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}
