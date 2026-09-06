package br.com.lactarehub.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

private val LactareColorScheme = lightColorScheme(
    primary = AppColors.Primary,
    onPrimary = AppColors.Surface,
    primaryContainer = AppColors.TintBlue,
    onPrimaryContainer = AppColors.PrimaryDark,
    secondary = AppColors.Accent,
    onSecondary = AppColors.Surface,
    secondaryContainer = AppColors.TintBlue,
    onSecondaryContainer = AppColors.Primary,
    tertiary = AppColors.PinkStrong,
    onTertiary = AppColors.Surface,
    tertiaryContainer = AppColors.PinkBg,
    onTertiaryContainer = AppColors.PinkFg,
    error = AppColors.Error,
    onError = AppColors.Surface,
    errorContainer = AppColors.ErrorContainer,
    onErrorContainer = AppColors.OnErrorContainer,
    background = AppColors.BgApp,
    onBackground = AppColors.Ink,
    surface = AppColors.Surface,
    onSurface = AppColors.Ink,
    surfaceVariant = AppColors.TintBlue,
    onSurfaceVariant = AppColors.InkMuted,
    surfaceContainerLowest = AppColors.Surface,
    surfaceContainerLow = AppColors.BgApp,
    surfaceContainer = AppColors.BgLanding,
    outline = AppColors.Border,
    outlineVariant = AppColors.BorderInput,
    inverseSurface = AppColors.Ink,
    inverseOnSurface = AppColors.Surface,
    inversePrimary = AppColors.Accent,
)

private val LactareTypography = Typography(
    displayLarge = AppTextStyles.heroTitle,
    displayMedium = AppTextStyles.screenTitle,
    displaySmall = AppTextStyles.sectionTitle,
    headlineLarge = AppTextStyles.sectionTitle,
    headlineMedium = AppTextStyles.sectionTitleOnBlue,
    headlineSmall = AppTextStyles.cardTitleBlue,
    titleLarge = AppTextStyles.cardTitle,
    titleMedium = AppTextStyles.cardTitleBlue,
    titleSmall = AppTextStyles.label,
    bodyLarge = AppTextStyles.body,
    bodyMedium = AppTextStyles.bodySmall,
    bodySmall = AppTextStyles.caption,
    labelLarge = AppTextStyles.button,
    labelMedium = AppTextStyles.badge,
    labelSmall = AppTextStyles.overline,
)

@Composable
fun LactareHubTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LactareColorScheme,
        typography = LactareTypography,
        content = content,
    )
}

val DefaultTextStyle: TextStyle get() = AppTextStyles.body
