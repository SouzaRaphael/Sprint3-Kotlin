package br.com.lactarehub.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles

enum class FilterChipStyle {
    TINTED,

    SOLID,
}

@Composable
fun FilterChipBar(
    labels: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    style: FilterChipStyle = FilterChipStyle.TINTED,
    contentPadding: PaddingValues = AppSpacing.pageHorizontal,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(labels.size) { index ->
            FilterChipItem(
                label = labels[index],
                isSelected = index == selectedIndex,
                style = style,
                onClick = { onSelected(index) },
            )
        }
    }
}

@Composable
private fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    style: FilterChipStyle,
    onClick: () -> Unit,
) {
    val background: Color
    val foreground: Color
    val borderColor: Color

    when {
        !isSelected -> {
            background = AppColors.Surface
            foreground = AppColors.PrimaryDark
            borderColor = AppColors.Border
        }

        style == FilterChipStyle.SOLID -> {
            background = AppColors.Primary
            foreground = AppColors.Surface
            borderColor = AppColors.Primary
        }

        else -> {
            background = AppColors.TintBlue
            foreground = AppColors.Primary
            borderColor = AppColors.TintBlue
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clip(AppRadius.pillShape)
            .background(background)
            .border(1.dp, borderColor, AppRadius.pillShape)
            .clickable(onClick = onClick)
            .padding(horizontal = AppSpacing.lg),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = label, style = AppTextStyles.chip.copy(color = foreground))
    }
}

@Composable
fun SegmentedFilter(
    labels: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.TintBlue, AppRadius.pillShape)
            .padding(4.dp),
    ) {
        labels.forEachIndexed { index, label ->
            Segment(
                label = label,
                isSelected = index == selectedIndex,
                onClick = { onSelected(index) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun Segment(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(38.dp)
            .clip(AppRadius.pillShape)
            .background(if (isSelected) AppColors.Surface else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isSelected) AppColors.Border else Color.Transparent,
                shape = AppRadius.pillShape,
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            maxLines = 1,
            style = AppTextStyles.chip.copy(
                color = if (isSelected) AppColors.PrimaryDark else AppColors.Primary,
            ),
        )
    }
}
