package br.com.lactarehub.core.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Família tipográfica do aplicativo.
 *
 * O projeto Flutter usava Plus Jakarta Sans via `google_fonts`. No Android o
 * equivalente seria o provedor de fontes do Google Play Services, que exige o
 * array de certificados `com_google_android_gms_fonts_certs` — não distribuído
 * por nenhuma dependência AndroidX atual. Como toda a identidade tipográfica do
 * design está nos pesos, tamanhos e espaçamentos definidos abaixo, usamos a
 * sans-serif do sistema: trocar esta constante por uma `FontFamily` de arquivos
 * `.ttf` em `res/font` é suficiente para recuperar a fonte original.
 */
val LactareFontFamily: FontFamily = FontFamily.SansSerif

/** Estilos tipográficos do Lactare. */
object AppTextStyles {

    private fun family(
        size: Double,
        weight: FontWeight,
        color: Color = AppColors.Ink,
        height: Double? = null,
        letterSpacing: Double = 0.0,
        fontStyle: FontStyle = FontStyle.Normal,
    ) = TextStyle(
        fontFamily = LactareFontFamily,
        fontSize = size.sp,
        fontWeight = weight,
        color = color,
        // No Flutter `height` é um multiplicador do tamanho da fonte.
        lineHeight = height?.let { (size * it).sp } ?: TextUnit.Unspecified,
        letterSpacing = letterSpacing.sp,
        fontStyle = fontStyle,
    )

    // ── Display — headline da landing ────────────────────────────
    val heroTitle = family(size = 34.0, weight = FontWeight.ExtraBold, height = 1.15, letterSpacing = -0.8)

    val heroTitleAccent = family(
        size = 34.0,
        weight = FontWeight.ExtraBold,
        height = 1.15,
        letterSpacing = -0.8,
        color = AppColors.PrimaryDeep,
        fontStyle = FontStyle.Italic,
    )

    // ── Títulos ──────────────────────────────────────────────────
    val screenTitle = family(
        size = 24.0,
        weight = FontWeight.ExtraBold,
        color = AppColors.PrimaryDark,
        letterSpacing = -0.4,
    )

    val sectionTitle = family(size = 20.0, weight = FontWeight.ExtraBold, letterSpacing = -0.3)

    val sectionTitleOnBlue = family(
        size = 20.0,
        weight = FontWeight.ExtraBold,
        color = AppColors.PrimaryDark,
        letterSpacing = -0.3,
    )

    val cardTitle = family(size = 16.0, weight = FontWeight.Bold)

    val cardTitleBlue = family(size = 16.0, weight = FontWeight.Bold, color = AppColors.PrimaryDark)

    val appBarTitle = family(size = 17.0, weight = FontWeight.ExtraBold, color = AppColors.PrimaryDark)

    // ── Corpo ────────────────────────────────────────────────────
    val body = family(size = 15.0, weight = FontWeight.Medium, color = AppColors.InkMuted, height = 1.55)

    val bodySmall = family(size = 13.0, weight = FontWeight.Medium, color = AppColors.InkMuted, height = 1.5)

    val quote = family(
        size = 14.5,
        weight = FontWeight.Medium,
        color = AppColors.InkMuted,
        height = 1.7,
        fontStyle = FontStyle.Italic,
    )

    // ── Números e estatísticas ───────────────────────────────────
    val statValue = family(size = 28.0, weight = FontWeight.ExtraBold, letterSpacing = -0.6)

    val statValueOnDark = family(
        size = 28.0,
        weight = FontWeight.ExtraBold,
        color = AppColors.Surface,
        letterSpacing = -0.6,
    )

    val statLabel = family(size = 12.5, weight = FontWeight.Medium, color = AppColors.InkMuted, height = 1.4)

    // ── Rótulos e componentes ────────────────────────────────────
    val label = family(size = 13.5, weight = FontWeight.Bold, color = AppColors.PrimaryDark)

    val overline = family(
        size = 11.5,
        weight = FontWeight.Bold,
        color = AppColors.InkMuted,
        letterSpacing = 1.4,
    )

    val badge = family(size = 11.5, weight = FontWeight.Bold, letterSpacing = 0.2)

    val chip = family(size = 13.5, weight = FontWeight.Bold, color = AppColors.Primary)

    val button = family(
        size = 15.5,
        weight = FontWeight.Bold,
        color = AppColors.Surface,
        letterSpacing = 0.1,
    )

    val navItem = family(size = 11.0, weight = FontWeight.SemiBold, color = AppColors.NavInactive)

    val caption = family(size = 12.0, weight = FontWeight.Medium, color = AppColors.InkMuted)

    val wordmark = family(
        size = 21.0,
        weight = FontWeight.ExtraBold,
        color = AppColors.Primary,
        letterSpacing = -0.4,
    )
}

/**
 * Reaproveita um estilo mudando só o tamanho, preservando a proporção da
 * entrelinha — o equivalente ao `copyWith(fontSize: …)` do Flutter.
 */
fun TextStyle.withSize(size: Double): TextStyle {
    val keepsRatio = lineHeight != TextUnit.Unspecified && fontSize != TextUnit.Unspecified
    return copy(
        fontSize = size.sp,
        lineHeight = if (keepsRatio) (size * (lineHeight.value / fontSize.value)).sp else lineHeight,
    )
}
