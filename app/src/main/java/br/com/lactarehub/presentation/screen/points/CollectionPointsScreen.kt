package br.com.lactarehub.presentation.screen.points

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.cardShadow
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.FilterChipBar
import br.com.lactarehub.presentation.component.FilterChipStyle
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.viewmodel.CollectionPointsViewModel

/** Tela 06 do protótipo — mapa dos pontos da rede. */
@Composable
fun CollectionPointsScreen(
    viewModel: CollectionPointsViewModel,
    onOpenPoint: (CollectionPoint) -> Unit,
    modifier: Modifier = Modifier,
) {
    val feedback = LocalAppFeedback.current
    val selected = viewModel.selected

    Box(modifier = modifier.fillMaxSize()) {
        MapCanvas()

        if (!viewModel.isLoading) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val width = maxWidth
                val height = maxHeight

                CurrentLocationDot(
                    modifier = Modifier.offset(
                        x = width * 0.5f - 15.dp,
                        y = height * 0.52f - 15.dp,
                    ),
                )

                viewModel.points.forEach { point ->
                    MapPin(
                        isSelected = point == selected,
                        onClick = { viewModel.select(point) },
                        modifier = Modifier.offset(
                            x = width * point.mapX - 17.dp,
                            y = height * point.mapY - 21.dp,
                        ),
                    )
                }
            }
        }

        Column(modifier = Modifier.safeTopPadding()) {
            SearchBar(
                query = viewModel.query,
                onQueryChange = viewModel::search,
                onlyOpenNow = viewModel.onlyOpenNow,
                onToggleOpenNow = viewModel::toggleOpenNow,
                modifier = Modifier.padding(AppSpacing.lg),
            )
            FilterChipBar(
                labels = viewModel.filterLabels,
                selectedIndex = viewModel.selectedFilterIndex,
                onSelected = viewModel::selectFilter,
                style = FilterChipStyle.SOLID,
                contentPadding = PaddingValues(horizontal = AppSpacing.lg),
            )
        }

        if (viewModel.isLoading) {
            CircularProgressIndicator(
                color = AppColors.Primary,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        if (!viewModel.isLoading && viewModel.points.isEmpty()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(AppSpacing.xl)
                    .cardShadow(AppRadius.largeCard)
                    .background(AppColors.Surface, AppRadius.largeCard)
                    .padding(AppSpacing.xl),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    AppIcons.LocationOff,
                    contentDescription = null,
                    tint = AppColors.NavInactive,
                    modifier = Modifier.size(32.dp),
                )
                Spacer(Modifier.height(AppSpacing.md))
                Text(
                    text = "Nenhum ponto encontrado com esses filtros.",
                    textAlign = TextAlign.Center,
                    style = AppTextStyles.bodySmall,
                )
            }
        }

        if (selected != null) {
            PointPreviewCard(
                point = selected,
                onOpenDetails = { onOpenPoint(selected) },
                onCall = { feedback.info("Ligando para ${selected.name}: ${selected.phone}") },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

/** Busca e alternador "Aberto agora", flutuando sobre o mapa. */
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onlyOpenNow: Boolean,
    onToggleOpenNow: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .cardShadow(AppRadius.pillShape)
            .background(AppColors.Surface, AppRadius.pillShape)
            .padding(start = AppSpacing.lg, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            AppIcons.Search,
            contentDescription = null,
            tint = AppColors.NavInactive,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.width(AppSpacing.sm))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (query.isEmpty()) {
                Text(text = "BLHs próximos", style = AppTextStyles.bodySmall)
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = AppTextStyles.bodySmall.copy(color = AppColors.Ink),
                cursorBrush = SolidColor(AppColors.Primary),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Box(
            modifier = Modifier
                .clip(AppRadius.pillShape)
                .background(if (onlyOpenNow) AppColors.Primary else AppColors.TintBlue)
                .clickable(onClick = onToggleOpenNow)
                .padding(horizontal = AppSpacing.md, vertical = 9.dp),
        ) {
            Text(
                text = "Aberto agora",
                style = AppTextStyles.badge.copy(
                    color = if (onlyOpenNow) AppColors.Surface else AppColors.Primary,
                ),
            )
        }
    }
}
