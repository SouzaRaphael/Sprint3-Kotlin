package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.TrackingStep
import br.com.lactarehub.domain.entity.TrackingStepStatus

@Composable
fun TrackingTimeline(steps: List<TrackingStep>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        steps.forEachIndexed { index, step ->
            TimelineRow(step = step, isLast = index == steps.lastIndex)
        }
    }
}

@Composable
private fun TimelineRow(step: TrackingStep, isLast: Boolean) {
    val isDone = step.status == TrackingStepStatus.CONCLUIDA
    val isCurrent = step.status == TrackingStepStatus.ATUAL
    val isPending = step.status == TrackingStepStatus.PENDENTE

    val dotColor = when (step.status) {
        TrackingStepStatus.CONCLUIDA -> AppColors.Accent
        TrackingStepStatus.ATUAL -> AppColors.Primary
        TrackingStepStatus.PENDENTE -> AppColors.Surface
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .padding(top = if (isCurrent) 2.dp else 4.dp)
                    .size(if (isCurrent) 20.dp else 16.dp)
                    .background(dotColor, CircleShape)
                    .then(
                        if (isPending) Modifier.border(2.dp, AppColors.Accent, CircleShape) else Modifier,
                    ),
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp)
                        .width(2.dp)
                        .background(
                            if (isDone) AppColors.Accent else AppColors.Accent.copy(alpha = 0.35f),
                        ),
                )
            }
        }
        Spacer(Modifier.width(AppSpacing.md))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else AppSpacing.xl),
        ) {
            Text(
                text = step.title,
                style = AppTextStyles.label.withSize(15.0).copy(
                    color = if (isPending) AppColors.InkMuted else AppColors.PrimaryDark,
                ),
            )
            Spacer(Modifier.height(4.dp))
            Text(text = step.description, style = AppTextStyles.caption)
        }
    }
}
