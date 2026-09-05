package br.com.lactarehub.presentation.screen.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeBottomPadding
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.InfoNoteCard
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.StatusBadge

/** Leitura de um artigo. */
@Composable
fun ArticleDetailScreen(
    articleId: String,
    goBack: () -> Unit,
    viewModel: br.com.lactarehub.presentation.viewmodel.ArticleDetailViewModel = viewModel(),
) {
    val feedback = LocalAppFeedback.current

    LaunchedEffect(articleId) { viewModel.load(articleId) }

    val article = viewModel.article

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(
            title = article?.category?.label ?: "Conteúdo",
            onBack = goBack,
            trailing = {
                IconButton(
                    onClick = {
                        val saved = viewModel.toggleSaved()
                        feedback.success(
                            if (saved) {
                                "Artigo salvo na sua lista de leitura."
                            } else {
                                "Artigo removido da sua lista."
                            },
                        )
                    },
                ) {
                    Icon(
                        imageVector = if (viewModel.isSaved) {
                            AppIcons.Bookmark
                        } else {
                            AppIcons.BookmarkBorder
                        },
                        contentDescription = if (viewModel.isSaved) {
                            "Remover da lista"
                        } else {
                            "Salvar para ler"
                        },
                        tint = AppColors.Primary,
                    )
                }
            },
        )

        if (article == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingBox()
            }
            return@Column
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .safeBottomPadding(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(article.coverColor),
            )

            Column(
                modifier = Modifier.padding(
                    start = AppSpacing.page,
                    top = AppSpacing.xl,
                    end = AppSpacing.page,
                    bottom = AppSpacing.section,
                ),
            ) {
                StatusBadge(
                    label = article.readingLabel,
                    background = AppColors.TintBlue,
                    foreground = AppColors.Primary,
                    icon = AppIcons.Clock,
                )
                Spacer(Modifier.height(AppSpacing.lg))
                Text(text = article.title, style = AppTextStyles.heroTitle.withSize(26.0))
                Spacer(Modifier.height(AppSpacing.md))
                Text(text = article.summary, style = AppTextStyles.body)
                Spacer(Modifier.height(AppSpacing.lg))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        AppIcons.Author,
                        contentDescription = null,
                        tint = AppColors.NavInactive,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(text = article.author, style = AppTextStyles.caption)
                }

                Spacer(Modifier.height(AppSpacing.xl))
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(AppColors.Border),
                )
                Spacer(Modifier.height(AppSpacing.xl))

                article.paragraphs.forEach { paragraph ->
                    Text(
                        text = paragraph,
                        style = AppTextStyles.body.copy(color = AppColors.Ink),
                    )
                    Spacer(Modifier.height(AppSpacing.lg))
                }

                Spacer(Modifier.height(AppSpacing.md))
                InfoNoteCard(
                    icon = AppIcons.Support,
                    message = "Ficou com dúvida? A equipe do BLH mais próximo " +
                        "responde pelo WhatsApp cadastrado no seu perfil.",
                )
            }
        }
    }
}
