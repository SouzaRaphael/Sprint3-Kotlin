package br.com.lactarehub.presentation.screen.myarea

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.ArticleListTile
import br.com.lactarehub.presentation.component.AvatarCircle
import br.com.lactarehub.presentation.component.EmptyStateCard
import br.com.lactarehub.presentation.component.LactareLogo
import br.com.lactarehub.presentation.component.LactareLogoVariant
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.component.SectionTitle
import br.com.lactarehub.presentation.viewmodel.MyAreaViewModel

/** Tela 08 do protótipo — área pessoal da doadora. */
@Composable
fun MyAreaScreen(
    viewModel: MyAreaViewModel,
    onOpenSchedule: () -> Unit,
    onOpenArticle: (Article) -> Unit,
    onOpenDonation: (Donation) -> Unit,
    onOpenTestimonials: () -> Unit,
    onOpenProfile: () -> Unit,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LactareLogo(variant = LactareLogoVariant.ROUNDED)
                Spacer(Modifier.weight(1f))
                Icon(
                    AppIcons.Notifications,
                    contentDescription = "Notificações",
                    tint = AppColors.PrimaryDark,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(AppSpacing.md))
                AvatarCircle(
                    name = donor.fullName,
                    gradientIndex = donor.avatarGradientIndex,
                    size = 38.dp,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onOpenProfile),
                )
            }
            Spacer(Modifier.height(AppSpacing.xl))
            Text(text = "Olá,", style = AppTextStyles.body)
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${donor.firstName}, obrigado por fazer parte do Lactare",
                style = AppTextStyles.heroTitle.withSize(25.0),
            )
            Spacer(Modifier.height(AppSpacing.xl))
        }

        item {
            JourneyCard(donor = donor, schedule = schedule)
            Spacer(Modifier.height(AppSpacing.lg))
            PrimaryButton(
                label = "Agendar nova coleta",
                icon = AppIcons.CalendarMonth,
                showTrailingIcon = false,
                onClick = onOpenSchedule,
            )
            Spacer(Modifier.height(AppSpacing.xxl))
        }

        item {
            SectionTitle(title = "Rastreamento da sua doação")
            Spacer(Modifier.height(AppSpacing.lg))
            if (donation == null) {
                EmptyStateCard(
                    icon = AppIcons.Route,
                    title = "Nada em trânsito por enquanto",
                    message = "Assim que a sua primeira doação for coletada, o " +
                        "percurso completo aparece aqui.",
                )
            } else {
                TrackingCard(donation = donation, onClick = { onOpenDonation(donation) })
            }
            Spacer(Modifier.height(AppSpacing.xxl))
        }

        item {
            SectionTitle(
                title = "Suas conquistas",
                actionLabel = "Histórias",
                onAction = onOpenTestimonials,
            )
            Spacer(Modifier.height(AppSpacing.lg))
            AchievementsGrid(
                achievements = viewModel.achievements,
                onClick = { achievement ->
                    feedback.info("${achievement.title}: ${achievement.progressLabel}.")
                },
            )
            Spacer(Modifier.height(AppSpacing.xxl))
        }

        item {
            ReferralCard(
                onInvite = {
                    feedback.success("Convite pronto para compartilhar com quem você quiser.")
                },
            )
            Spacer(Modifier.height(AppSpacing.xxl))
            SectionTitle(title = "Para você ler agora")
            Spacer(Modifier.height(AppSpacing.lg))
        }

        items(viewModel.readings.size) { index ->
            ArticleListTile(article = viewModel.readings[index], onClick = onOpenArticle)
            Spacer(Modifier.height(AppSpacing.md))
        }
    }
}
