@file:Suppress("UnusedPrivateProperty") // use when contrast themes are supported

package org.jdc.kmp.template.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.jdc.kmp.template.ui.theme.backgroundDark
import org.jdc.kmp.template.ui.theme.backgroundDarkHighContrast
import org.jdc.kmp.template.ui.theme.backgroundDarkMediumContrast
import org.jdc.kmp.template.ui.theme.backgroundLight
import org.jdc.kmp.template.ui.theme.backgroundLightHighContrast
import org.jdc.kmp.template.ui.theme.backgroundLightMediumContrast
import org.jdc.kmp.template.ui.theme.customColorAContainerDark
import org.jdc.kmp.template.ui.theme.customColorAContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.customColorAContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.customColorAContainerLight
import org.jdc.kmp.template.ui.theme.customColorAContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.customColorAContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.customColorADark
import org.jdc.kmp.template.ui.theme.customColorADarkHighContrast
import org.jdc.kmp.template.ui.theme.customColorADarkMediumContrast
import org.jdc.kmp.template.ui.theme.customColorALight
import org.jdc.kmp.template.ui.theme.customColorALightHighContrast
import org.jdc.kmp.template.ui.theme.customColorALightMediumContrast
import org.jdc.kmp.template.ui.theme.customColorBContainerDark
import org.jdc.kmp.template.ui.theme.customColorBContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.customColorBContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.customColorBContainerLight
import org.jdc.kmp.template.ui.theme.customColorBContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.customColorBContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.customColorBDark
import org.jdc.kmp.template.ui.theme.customColorBDarkHighContrast
import org.jdc.kmp.template.ui.theme.customColorBDarkMediumContrast
import org.jdc.kmp.template.ui.theme.customColorBLight
import org.jdc.kmp.template.ui.theme.customColorBLightHighContrast
import org.jdc.kmp.template.ui.theme.customColorBLightMediumContrast
import org.jdc.kmp.template.ui.theme.errorContainerDark
import org.jdc.kmp.template.ui.theme.errorContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.errorContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.errorContainerLight
import org.jdc.kmp.template.ui.theme.errorContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.errorContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.errorDark
import org.jdc.kmp.template.ui.theme.errorDarkHighContrast
import org.jdc.kmp.template.ui.theme.errorDarkMediumContrast
import org.jdc.kmp.template.ui.theme.errorLight
import org.jdc.kmp.template.ui.theme.errorLightHighContrast
import org.jdc.kmp.template.ui.theme.errorLightMediumContrast
import org.jdc.kmp.template.ui.theme.inverseOnSurfaceDark
import org.jdc.kmp.template.ui.theme.inverseOnSurfaceDarkHighContrast
import org.jdc.kmp.template.ui.theme.inverseOnSurfaceDarkMediumContrast
import org.jdc.kmp.template.ui.theme.inverseOnSurfaceLight
import org.jdc.kmp.template.ui.theme.inverseOnSurfaceLightHighContrast
import org.jdc.kmp.template.ui.theme.inverseOnSurfaceLightMediumContrast
import org.jdc.kmp.template.ui.theme.inversePrimaryDark
import org.jdc.kmp.template.ui.theme.inversePrimaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.inversePrimaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.inversePrimaryLight
import org.jdc.kmp.template.ui.theme.inversePrimaryLightHighContrast
import org.jdc.kmp.template.ui.theme.inversePrimaryLightMediumContrast
import org.jdc.kmp.template.ui.theme.inverseSurfaceDark
import org.jdc.kmp.template.ui.theme.inverseSurfaceDarkHighContrast
import org.jdc.kmp.template.ui.theme.inverseSurfaceDarkMediumContrast
import org.jdc.kmp.template.ui.theme.inverseSurfaceLight
import org.jdc.kmp.template.ui.theme.inverseSurfaceLightHighContrast
import org.jdc.kmp.template.ui.theme.inverseSurfaceLightMediumContrast
import org.jdc.kmp.template.ui.theme.onBackgroundDark
import org.jdc.kmp.template.ui.theme.onBackgroundDarkHighContrast
import org.jdc.kmp.template.ui.theme.onBackgroundDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onBackgroundLight
import org.jdc.kmp.template.ui.theme.onBackgroundLightHighContrast
import org.jdc.kmp.template.ui.theme.onBackgroundLightMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorAContainerDark
import org.jdc.kmp.template.ui.theme.onCustomColorAContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorAContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorAContainerLight
import org.jdc.kmp.template.ui.theme.onCustomColorAContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorAContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorADark
import org.jdc.kmp.template.ui.theme.onCustomColorADarkHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorADarkMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorALight
import org.jdc.kmp.template.ui.theme.onCustomColorALightHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorALightMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBContainerDark
import org.jdc.kmp.template.ui.theme.onCustomColorBContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBContainerLight
import org.jdc.kmp.template.ui.theme.onCustomColorBContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBDark
import org.jdc.kmp.template.ui.theme.onCustomColorBDarkHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBLight
import org.jdc.kmp.template.ui.theme.onCustomColorBLightHighContrast
import org.jdc.kmp.template.ui.theme.onCustomColorBLightMediumContrast
import org.jdc.kmp.template.ui.theme.onErrorContainerDark
import org.jdc.kmp.template.ui.theme.onErrorContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.onErrorContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onErrorContainerLight
import org.jdc.kmp.template.ui.theme.onErrorContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.onErrorContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.onErrorDark
import org.jdc.kmp.template.ui.theme.onErrorDarkHighContrast
import org.jdc.kmp.template.ui.theme.onErrorDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onErrorLight
import org.jdc.kmp.template.ui.theme.onErrorLightHighContrast
import org.jdc.kmp.template.ui.theme.onErrorLightMediumContrast
import org.jdc.kmp.template.ui.theme.onPrimaryContainerDark
import org.jdc.kmp.template.ui.theme.onPrimaryContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.onPrimaryContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onPrimaryContainerLight
import org.jdc.kmp.template.ui.theme.onPrimaryContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.onPrimaryContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.onPrimaryDark
import org.jdc.kmp.template.ui.theme.onPrimaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.onPrimaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onPrimaryLight
import org.jdc.kmp.template.ui.theme.onPrimaryLightHighContrast
import org.jdc.kmp.template.ui.theme.onPrimaryLightMediumContrast
import org.jdc.kmp.template.ui.theme.onSecondaryContainerDark
import org.jdc.kmp.template.ui.theme.onSecondaryContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.onSecondaryContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onSecondaryContainerLight
import org.jdc.kmp.template.ui.theme.onSecondaryContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.onSecondaryContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.onSecondaryDark
import org.jdc.kmp.template.ui.theme.onSecondaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.onSecondaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onSecondaryLight
import org.jdc.kmp.template.ui.theme.onSecondaryLightHighContrast
import org.jdc.kmp.template.ui.theme.onSecondaryLightMediumContrast
import org.jdc.kmp.template.ui.theme.onSurfaceDark
import org.jdc.kmp.template.ui.theme.onSurfaceDarkHighContrast
import org.jdc.kmp.template.ui.theme.onSurfaceDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onSurfaceLight
import org.jdc.kmp.template.ui.theme.onSurfaceLightHighContrast
import org.jdc.kmp.template.ui.theme.onSurfaceLightMediumContrast
import org.jdc.kmp.template.ui.theme.onSurfaceVariantDark
import org.jdc.kmp.template.ui.theme.onSurfaceVariantDarkHighContrast
import org.jdc.kmp.template.ui.theme.onSurfaceVariantDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onSurfaceVariantLight
import org.jdc.kmp.template.ui.theme.onSurfaceVariantLightHighContrast
import org.jdc.kmp.template.ui.theme.onSurfaceVariantLightMediumContrast
import org.jdc.kmp.template.ui.theme.onTertiaryContainerDark
import org.jdc.kmp.template.ui.theme.onTertiaryContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.onTertiaryContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onTertiaryContainerLight
import org.jdc.kmp.template.ui.theme.onTertiaryContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.onTertiaryContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.onTertiaryDark
import org.jdc.kmp.template.ui.theme.onTertiaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.onTertiaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.onTertiaryLight
import org.jdc.kmp.template.ui.theme.onTertiaryLightHighContrast
import org.jdc.kmp.template.ui.theme.onTertiaryLightMediumContrast
import org.jdc.kmp.template.ui.theme.outlineDark
import org.jdc.kmp.template.ui.theme.outlineDarkHighContrast
import org.jdc.kmp.template.ui.theme.outlineDarkMediumContrast
import org.jdc.kmp.template.ui.theme.outlineLight
import org.jdc.kmp.template.ui.theme.outlineLightHighContrast
import org.jdc.kmp.template.ui.theme.outlineLightMediumContrast
import org.jdc.kmp.template.ui.theme.outlineVariantDark
import org.jdc.kmp.template.ui.theme.outlineVariantDarkHighContrast
import org.jdc.kmp.template.ui.theme.outlineVariantDarkMediumContrast
import org.jdc.kmp.template.ui.theme.outlineVariantLight
import org.jdc.kmp.template.ui.theme.outlineVariantLightHighContrast
import org.jdc.kmp.template.ui.theme.outlineVariantLightMediumContrast
import org.jdc.kmp.template.ui.theme.primaryContainerDark
import org.jdc.kmp.template.ui.theme.primaryContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.primaryContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.primaryContainerLight
import org.jdc.kmp.template.ui.theme.primaryContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.primaryContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.primaryDark
import org.jdc.kmp.template.ui.theme.primaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.primaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.primaryLight
import org.jdc.kmp.template.ui.theme.primaryLightHighContrast
import org.jdc.kmp.template.ui.theme.primaryLightMediumContrast
import org.jdc.kmp.template.ui.theme.scrimDark
import org.jdc.kmp.template.ui.theme.scrimDarkHighContrast
import org.jdc.kmp.template.ui.theme.scrimDarkMediumContrast
import org.jdc.kmp.template.ui.theme.scrimLight
import org.jdc.kmp.template.ui.theme.scrimLightHighContrast
import org.jdc.kmp.template.ui.theme.scrimLightMediumContrast
import org.jdc.kmp.template.ui.theme.secondaryContainerDark
import org.jdc.kmp.template.ui.theme.secondaryContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.secondaryContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.secondaryContainerLight
import org.jdc.kmp.template.ui.theme.secondaryContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.secondaryContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.secondaryDark
import org.jdc.kmp.template.ui.theme.secondaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.secondaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.secondaryLight
import org.jdc.kmp.template.ui.theme.secondaryLightHighContrast
import org.jdc.kmp.template.ui.theme.secondaryLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceBrightDark
import org.jdc.kmp.template.ui.theme.surfaceBrightDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceBrightDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceBrightLight
import org.jdc.kmp.template.ui.theme.surfaceBrightLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceBrightLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerDark
import org.jdc.kmp.template.ui.theme.surfaceContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighDark
import org.jdc.kmp.template.ui.theme.surfaceContainerHighDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighLight
import org.jdc.kmp.template.ui.theme.surfaceContainerHighLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighestDark
import org.jdc.kmp.template.ui.theme.surfaceContainerHighestDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighestDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighestLight
import org.jdc.kmp.template.ui.theme.surfaceContainerHighestLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerHighestLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLight
import org.jdc.kmp.template.ui.theme.surfaceContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowDark
import org.jdc.kmp.template.ui.theme.surfaceContainerLowDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowLight
import org.jdc.kmp.template.ui.theme.surfaceContainerLowLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowestDark
import org.jdc.kmp.template.ui.theme.surfaceContainerLowestDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowestDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowestLight
import org.jdc.kmp.template.ui.theme.surfaceContainerLowestLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceContainerLowestLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceDark
import org.jdc.kmp.template.ui.theme.surfaceDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceDimDark
import org.jdc.kmp.template.ui.theme.surfaceDimDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceDimDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceDimLight
import org.jdc.kmp.template.ui.theme.surfaceDimLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceDimLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceLight
import org.jdc.kmp.template.ui.theme.surfaceLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceLightMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceVariantDark
import org.jdc.kmp.template.ui.theme.surfaceVariantDarkHighContrast
import org.jdc.kmp.template.ui.theme.surfaceVariantDarkMediumContrast
import org.jdc.kmp.template.ui.theme.surfaceVariantLight
import org.jdc.kmp.template.ui.theme.surfaceVariantLightHighContrast
import org.jdc.kmp.template.ui.theme.surfaceVariantLightMediumContrast
import org.jdc.kmp.template.ui.theme.tertiaryContainerDark
import org.jdc.kmp.template.ui.theme.tertiaryContainerDarkHighContrast
import org.jdc.kmp.template.ui.theme.tertiaryContainerDarkMediumContrast
import org.jdc.kmp.template.ui.theme.tertiaryContainerLight
import org.jdc.kmp.template.ui.theme.tertiaryContainerLightHighContrast
import org.jdc.kmp.template.ui.theme.tertiaryContainerLightMediumContrast
import org.jdc.kmp.template.ui.theme.tertiaryDark
import org.jdc.kmp.template.ui.theme.tertiaryDarkHighContrast
import org.jdc.kmp.template.ui.theme.tertiaryDarkMediumContrast
import org.jdc.kmp.template.ui.theme.tertiaryLight
import org.jdc.kmp.template.ui.theme.tertiaryLightHighContrast
import org.jdc.kmp.template.ui.theme.tertiaryLightMediumContrast

object AppTheme {
    val extendedColors: ExtendedColorScheme
        @Composable get() = LocalAppColors.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean,
//    dynamicTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
//    val colorScheme = when {
//        dynamicTheme -> {
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        else -> selectSchemeForContrast(darkTheme)
//    }
    val colorScheme = if (darkTheme) darkScheme else lightScheme

    val extendedAppColors = if (darkTheme) extendedDark else extendedLight

    CompositionLocalProvider(
        LocalAppColors provides extendedAppColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

//fun isContrastAvailable(): Boolean {
//    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
//}

//@Composable
//private fun selectSchemeForContrast(isDark: Boolean): ColorScheme {
//    val context = LocalContext.current
//    var colorScheme = if (isDark) darkScheme else lightScheme
//    val isPreview = LocalInspectionMode.current // TODO(b/336693596): UIModeManager is not yet supported in preview
//
//    return if (!isPreview && isContrastAvailable()) {
//        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
//        val contrastLevel = uiModeManager.contrast
//
//        colorScheme = when (contrastLevel) {
//            in 0.0f..0.33f -> if (isDark) darkScheme else lightScheme
//            in 0.34f..0.66f -> if (isDark) mediumContrastDarkColorScheme else mediumContrastLightColorScheme
//            in 0.67f..1.0f -> if (isDark) highContrastDarkColorScheme else highContrastLightColorScheme
//            else -> if (isDark) darkScheme else lightScheme
//        }
//        colorScheme
//    } else {
//        colorScheme
//    }
//}

private val LocalAppColors = staticCompositionLocalOf<ExtendedColorScheme> {
    error("No AppColorPalette provided")
}

@Immutable
data class ExtendedColorScheme(
    val customColorA: ColorFamily,
    val customColorB: ColorFamily,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

val unspecifiedScheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
    customColorA = ColorFamily(
        customColorALight,
        onCustomColorALight,
        customColorAContainerLight,
        onCustomColorAContainerLight,
    ),
    customColorB = ColorFamily(
        customColorBLight,
        onCustomColorBLight,
        customColorBContainerLight,
        onCustomColorBContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    customColorA = ColorFamily(
        customColorADark,
        onCustomColorADark,
        customColorAContainerDark,
        onCustomColorAContainerDark,
    ),
    customColorB = ColorFamily(
        customColorBDark,
        onCustomColorBDark,
        customColorBContainerDark,
        onCustomColorBContainerDark,
    ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
    customColorA = ColorFamily(
        customColorALightMediumContrast,
        onCustomColorALightMediumContrast,
        customColorAContainerLightMediumContrast,
        onCustomColorAContainerLightMediumContrast,
    ),
    customColorB = ColorFamily(
        customColorBLightMediumContrast,
        onCustomColorBLightMediumContrast,
        customColorBContainerLightMediumContrast,
        onCustomColorBContainerLightMediumContrast,
    ),
)

val extendedLightHighContrast = ExtendedColorScheme(
    customColorA = ColorFamily(
        customColorALightHighContrast,
        onCustomColorALightHighContrast,
        customColorAContainerLightHighContrast,
        onCustomColorAContainerLightHighContrast,
    ),
    customColorB = ColorFamily(
        customColorBLightHighContrast,
        onCustomColorBLightHighContrast,
        customColorBContainerLightHighContrast,
        onCustomColorBContainerLightHighContrast,
    ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    customColorA = ColorFamily(
        customColorADarkMediumContrast,
        onCustomColorADarkMediumContrast,
        customColorAContainerDarkMediumContrast,
        onCustomColorAContainerDarkMediumContrast,
    ),
    customColorB = ColorFamily(
        customColorBDarkMediumContrast,
        onCustomColorBDarkMediumContrast,
        customColorBContainerDarkMediumContrast,
        onCustomColorBContainerDarkMediumContrast,
    ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
    customColorA = ColorFamily(
        customColorADarkHighContrast,
        onCustomColorADarkHighContrast,
        customColorAContainerDarkHighContrast,
        onCustomColorAContainerDarkHighContrast,
    ),
    customColorB = ColorFamily(
        customColorBDarkHighContrast,
        onCustomColorBDarkHighContrast,
        customColorBContainerDarkHighContrast,
        onCustomColorBContainerDarkHighContrast,
    ),
)