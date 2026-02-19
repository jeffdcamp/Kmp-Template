package org.jdc.kmp.template.ux

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jdc.kmp.template.ux.about.AboutRoute
import org.jdc.kmp.template.ux.directory.DirectoryRoute
import org.jdc.kmp.template.ux.individual.IndividualRoute
import org.jdc.kmp.template.ux.individualedit.IndividualEditRoute

// Custom bridge serializer that encodes NavKey to JSON strings via kotlinx.serialization.
// This is needed because SavedState in KMP doesn't have Android's Bundle-backed serialization, so it falls back to kotlinx.serialization, which requires explicit polymorphic registration for
// interfaces.
// In short: it's a KMP limitation. On Android, the framework serializer handles things natively. On KMP (desktop/iOS/etc.), you must use the JSON bridge serializer,
// which requires the explicit polymorphic SerializersModule.
val NavKeySerializerModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(AboutRoute::class)
        subclass(DirectoryRoute::class)
        subclass(IndividualRoute::class)
        subclass(IndividualEditRoute::class)
    }
}

private val NavKeyJson = Json {
    serializersModule = NavKeySerializerModule
    ignoreUnknownKeys = true
}

object NavKeyBridgeSerializer : KSerializer<NavKey> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NavKeyBridge", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: NavKey) {
        val string = NavKeyJson.encodeToString(
            PolymorphicSerializer(NavKey::class),
            value
        )
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): NavKey {
        // We read the string back and decode it using the module
        val string = decoder.decodeString()
        return NavKeyJson.decodeFromString(
            PolymorphicSerializer(NavKey::class),
            string
        )
    }
}
