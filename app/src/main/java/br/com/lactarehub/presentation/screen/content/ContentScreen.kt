package br.com.lactarehub.presentation.screen.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.ArticleCard
import br.com.lactarehub.presentation.component.EmptyState
import br.com.lactarehub.presentation.component.FilterChipBar
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.viewmodel.ContentViewModel

@Composable
fun ContentScreen(
    viewModel: ContentViewModel,
    onOpenArticle: (Article) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(title = "Conteúdo")

        Column(
            modifier = Modifier.padding(
                start = AppSpacing.page,
                top = AppSpacing.xl,
                end = AppSpacing.page,
                bottom = AppSpacing.lg,
            ),
        ) {
            Text(text = "Aprenda com a gente", style = AppTextStyles.heroTitle.withSize(25.0))
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = "Conteúdos para apoiar sua jornada de amamentação e doação.",
                style = AppTextStyles.body,
            )
        }

        FilterChipBar(
            labels = viewModel.filterLabels,
            selectedIndex = viewModel.selectedFilterIndex,
            onSelected = viewModel::selectFilter,
        )
        Spacer(Modifier.height(AppSpacing.lg))

        when {
            viewModel.isLoading -> LoadingBox()

            viewModel.articles.isEmpty() -> EmptyState(
                message = "Ainda não há conteúdos publicados nessa categoria.",
            )

            else -> LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = AppSpacing.page,
                    end = AppSpacing.page,
                    bottom = AppSpacing.section,
                ),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.lg),
            ) {
                items(viewModel.articles, key = { it.id }) { article ->
                    ArticleCard(article = article, onClick = onOpenArticle)
                }
            }
        }
    }
}
