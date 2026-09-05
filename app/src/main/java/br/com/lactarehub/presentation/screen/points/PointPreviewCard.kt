package br.com.lactarehub.presentation.screen.points

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.cardShadow
import br.com.lactarehub.core.theme.safeBottomPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.DotStatusLabel
import br.com.lactarehub.presentation.component.PrimaryButton

/** Folha inferior com o resumo do ponto selecionado no mapa. */
@Composable
fun PointPreviewCard(
    point: CollectionPoint,
    onOpenDetails: () -> Unit,
    onCall: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .cardShadow(AppRadius.sheet)
            .background(AppColors.Surface, AppRadius.sheet)
            .safeBottomPadding()
            .padding(
                start = AppSpacing.lg,
                top = AppSpacing.md,
                end = AppSpacing.lg,
                bottom = AppSpacing.lg,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .width(42.dp)
                .height(4.dp)
                .background(AppColors.Border, RoundedCornerShape(2.dp)),
        )
        Spacer(Modifier.height(AppSpacing.lg))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AppRadius.card)
                .clickable(onClick = onOpenDetails),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(AppColors.CoverBlue, AppColors.Accent),
                        ),
                        shape = RoundedCornerShape(AppRadius.md),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    AppIcons.Drop,
                    contentDescription = null,
                    tint = AppColors.Surface,
                    modifier = Modifier.size(24.dp),
                )
            }
            Spacer(Modifier.width(AppSpacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = point.name,
                        style = AppTextStyles.cardTitleBlue.withSize(16.0),
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(Modifier.width(AppSpacing.sm))
                    DotStatusLabel(
                        label = if (point.isOpenNow) "Aberto" else "Fechado",
                        color = if (point.isOpenNow) AppColors.SuccessFg else AppColors.NavInactive,
                        modifier = Modifier.padding(end = AppSpacing.xs),
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(text = point.summary, style = AppTextStyles.caption)
                Spacer(Modifier.height(3.dp))
                Text(text = point.address, style = AppTextStyles.caption)
            }
        }

        Spacer(Modifier.height(AppSpacing.lg))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            PrimaryButton(
                label = "Traçar rota",
                icon = AppIcons.Route2,
                showTrailingIcon = false,
                height = 50.dp,
                onClick = onOpenDetails,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(AppSpacing.md))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.Border, CircleShape)
                    .clickable(onClick = onCall),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    AppIcons.Call,
                    contentDescription = "Ligar",
                    tint = AppColors.Primary,
                    modifier = Modifier.size(21.dp),
                )
            }
        }
    }
}
