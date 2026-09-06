package br.com.lactarehub.presentation.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.domain.entity.CollectionMode
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionPointType
import br.com.lactarehub.presentation.component.AppIcons
import java.time.LocalDate

@Composable
fun ModeSelector(
    selected: CollectionMode,
    onSelected: (CollectionMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CollectionMode.entries.forEach { mode ->
            ModeTile(
                mode = mode,
                isSelected = mode == selected,
                onClick = { onSelected(mode) },
                modifier = Modifier.padding(bottom = AppSpacing.sm),
            )
        }
    }
}

@Composable
private fun ModeTile(
    mode: CollectionMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val icon = when (mode) {
        CollectionMode.DOMICILIAR -> AppIcons.ModeHome
        CollectionMode.POSTO_DE_COLETA -> AppIcons.ModeStore
        CollectionMode.BANCO -> AppIcons.ModeHospital
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppRadius.card)
            .background(if (isSelected) AppColors.TintBlue else AppColors.Surface)
            .border(
                width = if (isSelected) 1.6.dp else 1.dp,
                color = if (isSelected) AppColors.Accent else AppColors.Border,
                shape = AppRadius.card,
            )
            .clickable(onClick = onClick)
            .padding(AppSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null, tint = AppColors.Primary, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(AppSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = mode.label, style = AppTextStyles.label)
            Spacer(Modifier.height(3.dp))
            Text(text = mode.description, style = AppTextStyles.caption)
        }
        Icon(
            imageVector = if (isSelected) AppIcons.RadioChecked else AppIcons.RadioUnchecked,
            contentDescription = null,
            tint = if (isSelected) AppColors.Primary else AppColors.Border,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Composable
fun DateStrip(
    dates: List<LocalDate>,
    selected: LocalDate?,
    onSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
    ) {
        items(dates) { date ->
            val isSelected = selected != null &&
                selected.dayOfMonth == date.dayOfMonth &&
                selected.monthValue == date.monthValue

            Column(
                modifier = Modifier
                    .width(62.dp)
                    .height(78.dp)
                    .clip(AppRadius.card)
                    .background(if (isSelected) AppColors.Primary else AppColors.Surface)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) AppColors.Primary else AppColors.Border,
                        shape = AppRadius.card,
                    )
                    .clickable { onSelected(date) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = Formatters.weekdayShort(date),
                    style = AppTextStyles.caption.copy(
                        color = if (isSelected) {
                            AppColors.Surface.copy(alpha = 0.8f)
                        } else {
                            AppColors.InkMuted
                        },
                    ),
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = date.dayOfMonth.toString().padStart(2, '0'),
                    style = AppTextStyles.cardTitle.withSize(19.0).copy(
                        color = if (isSelected) AppColors.Surface else AppColors.PrimaryDark,
                    ),
                )
                Text(
                    text = Formatters.monthBadge(date),
                    style = AppTextStyles.badge.withSize(9.5).copy(
                        color = if (isSelected) {
                            AppColors.Surface.copy(alpha = 0.8f)
                        } else {
                            AppColors.NavInactive
                        },
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimeWindowGrid(
    windows: List<String>,
    selected: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.sm),
    ) {
        windows.forEach { window ->
            val isSelected = window == selected
            Box(
                modifier = Modifier
                    .clip(AppRadius.pillShape)
                    .background(if (isSelected) AppColors.TintBlue else AppColors.Surface)
                    .border(
                        width = if (isSelected) 1.6.dp else 1.dp,
                        color = if (isSelected) AppColors.Accent else AppColors.Border,
                        shape = AppRadius.pillShape,
                    )
                    .clickable { onSelected(window) }
                    .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.md),
            ) {
                Text(
                    text = window,
                    style = AppTextStyles.chip.copy(
                        color = if (isSelected) AppColors.Primary else AppColors.PrimaryDark,
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointPicker(
    points: List<CollectionPoint>,
    selected: CollectionPoint?,
    onSelected: (CollectionPoint) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectable = points.filter { it.type != CollectionPointType.COLETA_DOMICILIAR }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Ponto de entrega", style = AppTextStyles.label)
        Spacer(Modifier.height(AppSpacing.sm))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Row(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(AppRadius.input)
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.BorderInput, AppRadius.input)
                    .padding(horizontal = AppSpacing.lg),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selected?.name ?: "Selecione um ponto",
                    style = AppTextStyles.bodySmall.copy(
                        color = if (selected == null) AppColors.InkMuted else AppColors.Ink,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    AppIcons.ExpandMore,
                    contentDescription = null,
                    tint = AppColors.NavInactive,
                )
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                selectable.forEach { point ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = point.name,
                                style = AppTextStyles.bodySmall.copy(color = AppColors.Ink),
                            )
                        },
                        onClick = {
                            onSelected(point)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}
