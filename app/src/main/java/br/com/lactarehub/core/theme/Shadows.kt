package br.com.lactarehub.core.theme

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Sombras e recuos de sistema reutilizados em todo o aplicativo.
 *
 * O Flutter descreve sombras com cor, desfoque e deslocamento; o Compose usa
 * elevação. Estes dois modificadores concentram a conversão para que os cards
 * mantenham o mesmo peso visual do protótipo.
 */

/** Sombra padrão dos cards claros. */
fun Modifier.cardShadow(shape: Shape): Modifier = shadow(
    elevation = 6.dp,
    shape = shape,
    clip = false,
    ambientColor = AppColors.ShadowCard,
    spotColor = AppColors.ShadowCard,
)

/** Sombra mais presente, usada nos botões primários e nos cards em gradiente. */
fun Modifier.buttonShadow(shape: Shape): Modifier = shadow(
    elevation = 10.dp,
    shape = shape,
    clip = false,
    ambientColor = AppColors.Primary,
    spotColor = AppColors.Primary,
)

/** Equivalente ao `SafeArea(bottom: false)` do Flutter. */
@Composable
fun Modifier.safeTopPadding(): Modifier = windowInsetsPadding(WindowInsets.statusBars)

/** Equivalente ao `SafeArea(top: false)` do Flutter. */
@Composable
fun Modifier.safeBottomPadding(): Modifier = windowInsetsPadding(WindowInsets.navigationBars)
