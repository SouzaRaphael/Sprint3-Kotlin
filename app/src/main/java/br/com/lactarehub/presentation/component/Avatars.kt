package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.util.Formatters

/** Avatar em gradiente com as iniciais — o design não usa fotografias. */
@Composable
fun AvatarCircle(
    name: String,
    gradientIndex: Int,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    showInitials: Boolean = true,
) {
    val colors = AppColors.avatarGradient(gradientIndex)

    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = Brush.linearGradient(colors),
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (showInitials) {
            Text(
                text = Formatters.initials(name),
                style = AppTextStyles.badge.copy(
                    color = AppColors.Surface,
                    fontSize = (size.value * 0.34f).sp,
                ),
            )
        }
    }
}

/** Círculo branco com sigla de estado, empilhado no hero da landing. */
@Composable
fun StateAvatar(state: String, modifier: Modifier = Modifier, size: Dp = 36.dp) {
    Box(
        modifier = modifier
            .size(size)
            .background(AppColors.Surface, CircleShape)
            .border(1.5.dp, AppColors.Border, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = state,
            style = AppTextStyles.badge.copy(
                color = AppColors.PrimaryDark,
                fontSize = (size.value * 0.3f).sp,
            ),
        )
    }
}
