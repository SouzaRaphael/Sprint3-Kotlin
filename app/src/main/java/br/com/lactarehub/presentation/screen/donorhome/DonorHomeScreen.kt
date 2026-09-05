package br.com.lactarehub.presentation.screen.donorhome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.ArticleCarouselCard
import br.com.lactarehub.presentation.component.EmptyStateCard
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.SectionTitle
import br.com.lactarehub.presentation.viewmodel.DonorHomeViewModel

/** Tela 07 do protótipo — home da doadora autenticada. */
@Composable
fun DonorHomeScreen(
    viewModel: DonorHomeViewModel,
    onOpenSchedule: () -> Unit,
    onOpenPoints: () -> Unit,
    onOpenContent: () -> Unit,
    onOpenMyArea: () -> Unit,
    onOpenArticle: (Article) -> Unit,
    onOpenDonation: (Donation) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {
    val feedback = LocalAppFeedback.current
    val donor = viewModel.donor
    val schedule = viewModel.schedule
    val donation = viewModel.currentDonation

    if (viewModel.isLoading || donor == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.BgApp),
            contentAlignment = Alignment.Center,
        ) {
            LoadingBox()
        }
        return
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
        contentPadding = PaddingValues(
            start = AppSpacing.page,
            top = AppSpacing.lg,
            end = AppSpacing.page,
            bottom = AppSpacing.section,
        ),
    ) {
        item {
            Greeting(name = donor.firstName, gradientIndex = donor.avatarGradientIndex)
            Spacer(Modifier.height(AppSpacing.xl))
        }

        item {
            if (schedule == null) {
                Text(
                    text = "Vamos marcar a sua primeira coleta.",
                    style = AppTextStyles.heroTitle.withSize(23.0),
                )
            } else {
                Text(
                    text = buildAnnotatedString {
                        append("Sua próxima coleta é ")
                        withStyle(SpanStyle(color = AppColors.Accent)) {
                            append(
                                Formatters.daysUntil(
                                    schedule.scheduledAt,
                                    schedule.referenceToday,
                                ),
                            )
                        }
                        append(".")
                    },
                    style = AppTextStyles.heroTitle.withSize(23.0),
                )
            }
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = if (schedule == null) {
                    "Bem-vinda à rede! O próximo passo é combinar como o seu " +
                        "leite chega até um banco de leite."
                } else {
                    "Em nome de muitas famílias, agradecemos a sua generosidade."
                },
                style = AppTextStyles.body,
            )
            Spacer(Modifier.height(AppSpacing.xl))
        }

        item {
            if (schedule == null) {
                EmptyStateCard(
                    icon = AppIcons.EventAvailable,
                    title = "Nenhuma coleta agendada",
                    message = "Escolha a modalidade, o dia e o horário que " +
                        "couberem na sua rotina.",
                    actionLabel = "Agendar coleta",
                    onAction = onOpenSchedule,
                    onDarkBackground = true,
                )
            } else {
                NextCollectionCard(
                    schedule = schedule,
                    isConfirming = viewModel.isConfirming,
                    onConfirm = {
                        viewModel.confirmCollection {
                            feedback.success("Coleta confirmada! A equipe já foi avisada.")
                        }
                    },
                    onReschedule = onOpenSchedule,
                )
            }
            Spacer(Modifier.height(AppSpacing.xl))
        }

        item {
            QuickActionsRow(
                actions = listOf(
                    QuickAction(
                        icon = AppIcons.Add,
                        label = "Nova coleta",
                        isHighlighted = true,
                        onClick = onOpenSchedule,
                    ),
                    QuickAction(
                        icon = AppIcons.TabPoints,
                        label = "Pontos",
                        onClick = onOpenPoints,
                    ),
                    QuickAction(
                        icon = AppIcons.Gift,
                        label = "Indicar",
                        onClick = {
                            feedback.info(
                                "Link de convite gerado! Compartilhe com quem você " +
                                    "quer trazer para a rede.",
                            )
                        },
                    ),
                    QuickAction(
                        icon = AppIcons.Learn,
                        label = "Aprender",
                        onClick = onOpenContent,
                    ),
                ),
            )
            Spacer(Modifier.height(AppSpacing.xxl))
        }

        item {
            SectionTitle(title = "Seu impacto até aqui")
            Spacer(Modifier.height(AppSpacing.lg))
            ImpactSummaryCard(donor = donor, onOpenAchievements = onOpenMyArea)
            Spacer(Modifier.height(AppSpacing.xxl))
        }

        item {
            SectionTitle(title = "Acompanhe sua doação")
            Spacer(Modifier.height(AppSpacing.lg))
            if (donation == null) {
                EmptyStateCard(
                    icon = AppIcons.Route,
                    title = "Seu rastreamento aparece aqui",
                    message = "Depois da primeira coleta você acompanha cada " +
                        "etapa, do frasco recolhido até a UTI neonatal.",
                )
            } else {
                DonationPreviewCard(donation = donation, onClick = onOpenDonation)
                Spacer(Modifier.height(AppSpacing.lg))
                TeamMessageCard(
                    donorFirstName = donor.firstName,
                    hospital = donation.destinationHospital,
                )
            }
            Spacer(Modifier.height(AppSpacing.xxl))
        }

        item {
            SectionTitle(title = "Para ler nesta semana")
            Spacer(Modifier.height(AppSpacing.lg))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.md),
                modifier = Modifier.height(210.dp),
            ) {
                items(viewModel.featuredArticles) { article ->
                    ArticleCarouselCard(article = article, onClick = onOpenArticle)
                }
            }
        }
    }
}
