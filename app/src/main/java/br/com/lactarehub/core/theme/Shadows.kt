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

fun Modifier.cardShadow(shape: Shape): Modifier = shadow(
    elevation = 6.dp,
    shape = shape,
    clip = false,
    ambientColor = AppColors.ShadowCard,
    spotColor = AppColors.ShadowCard,
)

fun Modifier.buttonShadow(shape: Shape): Modifier = shadow(
    elevation = 10.dp,
    shape = shape,
    clip = false,
    ambientColor = AppColors.Primary,
    spotColor = AppColors.Primary,
)

@Composable
fun Modifier.safeTopPadding(): Modifier = windowInsetsPadding(WindowInsets.statusBars)

@Composable
fun Modifier.safeBottomPadding(): Modifier = windowInsetsPadding(WindowInsets.navigationBars)
