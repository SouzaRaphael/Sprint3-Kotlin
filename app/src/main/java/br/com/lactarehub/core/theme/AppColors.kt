package br.com.lactarehub.core.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AppColors {
    val Primary = Color(0xFF00458B)
    val PrimaryDark = Color(0xFF002A55)
    val PrimaryDeep = Color(0xFF133E95)
    val Accent = Color(0xFF54B2E3)
    val AccentCyan = Color(0xFF0DA4DF)

    val Ink = Color(0xFF101828)
    val InkMuted = Color(0xFF4A5565)
    val NavInactive = Color(0xFF5A6B80)
    val BgLanding = Color(0xFFF9FAFB)
    val BgApp = Color(0xFFF7FBFD)
    val Surface = Color(0xFFFFFFFF)
    val Border = Color(0xFFE1E6F0)
    val BorderInput = Color(0xFFE3EDF5)
    val TintBlue = Color(0xFFEAF6FC)
    val Hint = Color(0xFFA6B4C4)

    val SuccessBg = Color(0xFFD8F7F5)
    val SuccessFg = Color(0xFF1B7F79)
    val PinkBg = Color(0xFFFDE6EF)
    val PinkFg = Color(0xFFB53272)
    val PinkStrong = Color(0xFFF25CA2)
    val WarningBg = Color(0xFFFDF0DC)
    val WarningFg = Color(0xFF9A6412)
    val Error = Color(0xFFC0334E)
    val ErrorContainer = Color(0xFFFCE3E8)
    val OnErrorContainer = Color(0xFF8E1F36)

    val CoverBlue = Color(0xFFB6E0F4)
    val CoverLilac = Color(0xFFEDD3F5)
    val CoverMint = Color(0xFF9BEFE9)
    val CoverPeach = Color(0xFFF9D9B8)
    val CoverRose = Color(0xFFFCE4EC)

    val MapBackground = Color(0xFFE8EFF5)
    val MapPark = Color(0xFFDCE9DE)
    val MapWater = Color(0xFFD6E6F2)
    val MapBlock = Color(0xFFCBD8E4)

    val ShadowCard = Color(0xFF0F2A4A)

    fun heroCard(widthPx: Float): Brush = Brush.linearGradient(
        colors = listOf(Color(0xFF054C93), Color(0xFF3185C8)),
        start = Offset(0f, 0f),
        end = Offset(widthPx, 0f),
    )

    fun heroBlob(radiusPx: Float, center: Offset): Brush = Brush.radialGradient(
        0.0f to Color(0xFFBEE9FF),
        0.55f to Color(0xFF2AB1F0),
        1.0f to Color(0xFF0E8FD8),
        center = center,
        radius = radiusPx,
    )

    val avatarGradients: List<List<Color>> = listOf(
        listOf(Color(0xFFF7A8C4), Color(0xFFD98BC7)),
        listOf(Color(0xFF56D6C6), Color(0xFF2FB5B0)),
        listOf(Color(0xFFF9A26C), Color(0xFFF2704B)),
        listOf(Color(0xFF6BA8E8), Color(0xFF3C6FD1)),
        listOf(Color(0xFF9BE7DF), Color(0xFF63C6D6)),
        listOf(Color(0xFFC9A7F0), Color(0xFF9B6FE0)),
    )

    fun avatarGradient(index: Int): List<Color> =
        avatarGradients[((index % avatarGradients.size) + avatarGradients.size) % avatarGradients.size]
}
