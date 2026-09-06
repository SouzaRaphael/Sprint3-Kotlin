package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.domain.entity.DonationStatus
import br.com.lactarehub.domain.entity.TestimonialType

@Composable
fun StatusBadge(
    label: String,
    background: Color,
    foreground: Color,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Row(
        modifier = modifier
            .background(background, AppRadius.pillShape)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = foreground, modifier = Modifier.size(13.dp))
            Spacer(Modifier.width(5.dp))
        }
        Text(text = label, style = AppTextStyles.badge.copy(color = foreground))
    }
}

@Composable
fun TestimonialBadge(type: TestimonialType, modifier: Modifier = Modifier) {
    val recurring = type == TestimonialType.RECORRENTE
    StatusBadge(
        label = type.label,
        background = if (recurring) AppColors.SuccessBg else AppColors.PinkBg,
        foreground = if (recurring) AppColors.SuccessFg else AppColors.PinkFg,
        modifier = modifier,
    )
}

@Composable
fun DonationBadge(status: DonationStatus, modifier: Modifier = Modifier) {
    val background = when (status) {
        DonationStatus.EM_ANALISE, DonationStatus.EM_ANDAMENTO -> AppColors.SuccessBg
        DonationStatus.APROVADA -> AppColors.TintBlue
        DonationStatus.DISTRIBUIDA -> AppColors.SuccessBg
    }
    val foreground = when (status) {
        DonationStatus.EM_ANALISE, DonationStatus.EM_ANDAMENTO -> AppColors.SuccessFg
        DonationStatus.APROVADA -> AppColors.Primary
        DonationStatus.DISTRIBUIDA -> AppColors.SuccessFg
    }
    StatusBadge(
        label = status.label,
        background = background,
        foreground = foreground,
        modifier = modifier,
    )
}

@Composable
fun DotStatusLabel(label: String, color: Color, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .background(color, CircleShape),
        )
        Spacer(Modifier.width(6.dp))
        Text(text = label, style = AppTextStyles.badge.copy(color = color))
    }
}
