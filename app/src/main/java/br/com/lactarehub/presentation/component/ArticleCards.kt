package br.com.lactarehub.presentation.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.Article

/** Card grande da aba Conteúdo: capa colorida, título, resumo e tempo. */
@Composable
fun ArticleCard(article: Article, onClick: (Article) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppRadius.largeCard)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .clickable { onClick(article) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(article.coverColor),
        )
        Column(modifier = Modifier.padding(AppSpacing.lg)) {
            Text(text = article.title, style = AppTextStyles.cardTitleBlue.withSize(16.0))
            Spacer(Modifier.height(AppSpacing.sm))
            Text(text = article.summary, style = AppTextStyles.bodySmall)
            Spacer(Modifier.height(AppSpacing.md))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    AppIcons.Clock,
                    contentDescription = null,
                    tint = AppColors.NavInactive,
                    modifier = Modifier.size(14.dp),
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = article.readingLabel,
                    style = AppTextStyles.caption,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    AppIcons.Forward,
                    contentDescription = null,
                    tint = AppColors.Primary,
                    modifier = Modifier.size(17.dp),
                )
            }
        }
    }
}

/** Card estreito do carrossel "Para ler nesta semana". */
@Composable
fun ArticleCarouselCard(
    article: Article,
    onClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 240.dp,
) {
    Column(
        modifier = modifier
            .width(width)
            .clip(AppRadius.largeCard)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .clickable { onClick(article) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(79.dp)
                .background(article.coverColor),
        )
        Column(modifier = Modifier.padding(AppSpacing.md)) {
            StatusBadge(
                label = article.category.label,
                background = AppColors.BgApp,
                foreground = AppColors.InkMuted,
            )
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = article.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = AppTextStyles.cardTitleBlue.withSize(14.5),
            )
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    AppIcons.Clock,
                    contentDescription = null,
                    tint = AppColors.NavInactive,
                    modifier = Modifier.size(13.dp),
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "${article.readingMinutes} min", style = AppTextStyles.caption)
            }
        }
    }
}

/** Linha compacta usada na lista de leituras da área da doadora. */
@Composable
fun ArticleListTile(article: Article, onClick: (Article) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppRadius.card)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.card)
            .clickable { onClick(article) }
            .padding(AppSpacing.md),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(article.coverColor, RoundedCornerShape(AppRadius.md)),
        )
        Spacer(Modifier.width(AppSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            StatusBadge(
                label = article.category.label,
                background = AppColors.BgApp,
                foreground = AppColors.InkMuted,
            )
            Spacer(Modifier.height(6.dp))
            Text(text = article.title, style = AppTextStyles.cardTitleBlue.withSize(14.5))
            Spacer(Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    AppIcons.Clock,
                    contentDescription = null,
                    tint = AppColors.NavInactive,
                    modifier = Modifier.size(13.dp),
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "${article.readingMinutes} min", style = AppTextStyles.caption)
            }
        }
    }
}
