package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppTextStyles

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    overline: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    color: Color = AppColors.PrimaryDark,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (overline != null) {
                Text(text = overline.uppercase(), style = AppTextStyles.overline)
                Spacer(Modifier.height(8.dp))
            }
            Text(text = title, style = AppTextStyles.sectionTitle.copy(color = color))
        }
        if (actionLabel != null && onAction != null) {
            Row(
                modifier = Modifier
                    .clickable(onClick = onAction)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = actionLabel, style = AppTextStyles.label.copy(color = AppColors.Primary))
                Spacer(Modifier.width(4.dp))
                Icon(
                    AppIcons.Forward,
                    contentDescription = null,
                    tint = AppColors.Primary,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
fun StatTile(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onDark: Boolean = false,
    valueColor: Color? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    val baseValueStyle = if (onDark) AppTextStyles.statValueOnDark else AppTextStyles.statValue

    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = value,
            style = if (valueColor == null) baseValueStyle else baseValueStyle.copy(color = valueColor),
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            textAlign = if (horizontalAlignment == Alignment.CenterHorizontally) {
                TextAlign.Center
            } else {
                TextAlign.Start
            },
            style = if (onDark) {
                AppTextStyles.statLabel.copy(color = AppColors.Surface.copy(alpha = 0.72f))
            } else {
                AppTextStyles.statLabel
            },
        )
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    bottomSpacing: Dp = 16.dp,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = bottomSpacing),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(AppColors.TintBlue, RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = AppColors.Primary, modifier = Modifier.size(19.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = AppTextStyles.caption)
            Spacer(Modifier.height(3.dp))
            Text(
                text = value.trim().ifEmpty { "—" },
                style = AppTextStyles.bodySmall.copy(
                    color = AppColors.Ink,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }
    }
}
