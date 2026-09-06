package br.com.lactarehub.presentation.screen.points

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.presentation.component.AppIcons

private val verticalRoads = listOf(0.18f, 0.46f, 0.74f)
private val horizontalRoads = listOf(0.26f, 0.55f, 0.82f)

private val parks = listOf(
    Triple(0.14f, 0.18f, 0.16f),
    Triple(0.82f, 0.14f, 0.13f),
    Triple(0.30f, 0.72f, 0.18f),
    Triple(0.88f, 0.66f, 0.15f),
)

@Composable
fun MapCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(color = AppColors.MapBackground)

        parks.forEach { (x, y, radius) ->
            drawCircle(
                color = AppColors.MapPark,
                radius = size.width * radius,
                center = Offset(size.width * x, size.height * y),
            )
        }

        drawCircle(
            color = AppColors.MapWater,
            radius = size.width * 0.22f,
            center = Offset(size.width * 0.62f, size.height * 0.36f),
        )

        for (i in 1 until 6) {
            val dx = size.width * i / 6f
            val dy = size.height * i / 6f
            drawLine(
                color = AppColors.MapBlock,
                start = Offset(dx, 0f),
                end = Offset(dx, size.height),
                strokeWidth = 1f,
            )
            drawLine(
                color = AppColors.MapBlock,
                start = Offset(0f, dy),
                end = Offset(size.width, dy),
                strokeWidth = 1f,
            )
        }

        verticalRoads.forEach { x ->
            drawLine(
                color = Color.White,
                start = Offset(size.width * x, -10f),
                end = Offset(size.width * x, size.height + 10f),
                strokeWidth = 13f,
                cap = StrokeCap.Round,
            )
        }
        horizontalRoads.forEach { y ->
            drawLine(
                color = Color.White,
                start = Offset(-10f, size.height * y),
                end = Offset(size.width + 10f, size.height * y),
                strokeWidth = 13f,
                cap = StrokeCap.Round,
            )
        }

        drawLine(
            color = Color.White,
            start = Offset(-10f, size.height * 0.92f),
            end = Offset(size.width + 10f, size.height * 0.08f),
            strokeWidth = 10f,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
fun MapPin(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 34.dp,
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.18f else 1f,
        label = "map-pin-scale",
    )
    val color = if (isSelected) AppColors.Accent else AppColors.Primary

    Column(
        modifier = modifier
            .scale(scale)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color, RoundedCornerShape(size * 0.45f))
                .border(2.5.dp, AppColors.Surface, RoundedCornerShape(size * 0.45f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                AppIcons.Drop,
                contentDescription = null,
                tint = AppColors.Surface,
                modifier = Modifier.size(size * 0.46f),
            )
        }
        Canvas(
            modifier = Modifier
                .offset(y = (-3).dp)
                .size(width = 12.dp, height = 8.dp),
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(this@Canvas.size.width / 2f, this@Canvas.size.height)
                lineTo(this@Canvas.size.width, 0f)
                close()
            }
            drawPath(path, color)
        }
    }
}

@Composable
fun CurrentLocationDot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(30.dp)
            .background(AppColors.Accent.copy(alpha = 0.28f), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(AppColors.Accent, CircleShape)
                .border(2.5.dp, AppColors.Surface, CircleShape),
        )
    }
}
