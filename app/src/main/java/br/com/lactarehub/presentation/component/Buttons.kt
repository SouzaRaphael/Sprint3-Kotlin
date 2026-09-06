package br.com.lactarehub.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.buttonShadow

@Composable
fun PrimaryButton(
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    trailingIcon: ImageVector = AppIcons.Forward,
    showTrailingIcon: Boolean = true,
    isLoading: Boolean = false,
    expand: Boolean = true,
    color: Color = AppColors.Primary,
    height: Dp = 56.dp,
) {
    val enabled = onClick != null && !isLoading

    Button(
        onClick = { onClick?.invoke() },
        enabled = enabled,
        shape = AppRadius.pillShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = AppColors.Surface,
            disabledContainerColor = color.copy(alpha = 0.45f),
            disabledContentColor = AppColors.Surface,
        ),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = modifier
            .then(if (expand) Modifier.fillMaxWidth() else Modifier)
            .height(height)
            .then(if (enabled) Modifier.buttonShadow(AppRadius.pillShape) else Modifier),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.4.dp,
                color = AppColors.Surface,
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = AppColors.Surface, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(10.dp))
                }
                Text(
                    text = label,
                    style = AppTextStyles.button,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (showTrailingIcon) {
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        trailingIcon,
                        contentDescription = null,
                        tint = AppColors.Surface,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun SecondaryButton(
    label: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    expand: Boolean = true,
    height: Dp = 56.dp,
    foregroundColor: Color = AppColors.PrimaryDark,
) {
    OutlinedButton(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        shape = AppRadius.pillShape,
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Border),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppColors.Surface,
            contentColor = foregroundColor,
            disabledContainerColor = AppColors.Surface,
            disabledContentColor = foregroundColor.copy(alpha = 0.5f),
        ),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = modifier
            .then(if (expand) Modifier.fillMaxWidth() else Modifier)
            .height(height),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = foregroundColor, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
            }
            Text(
                text = label,
                style = AppTextStyles.button.copy(color = foregroundColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (trailingIcon != null) {
                Spacer(Modifier.width(10.dp))
                Icon(
                    trailingIcon,
                    contentDescription = null,
                    tint = foregroundColor,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
