package br.com.lactarehub.core.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/** Escala de espaçamento do design. */
object AppSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 28.dp
    val section = 40.dp

    /** Margem lateral padrão das telas (20dp nas capturas de 390dp). */
    val page = 20.dp

    val pageHorizontal = PaddingValues(horizontal = page)

    /** Espaço reservado abaixo do conteúdo para a bottom navigation. */
    val bottomNavClearance = 96.dp
}

/** Raios de borda do design. */
object AppRadius {
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 20.dp
    val xxl = 24.dp
    val pill = 100.dp

    val card = RoundedCornerShape(lg)
    val largeCard = RoundedCornerShape(xl)
    val input = RoundedCornerShape(md)
    val pillShape = RoundedCornerShape(pill)
    val sheet = RoundedCornerShape(topStart = xxl, topEnd = xxl)
}
