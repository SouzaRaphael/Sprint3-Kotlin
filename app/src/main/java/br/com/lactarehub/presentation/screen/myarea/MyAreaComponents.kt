package br.com.lactarehub.presentation.screen.myarea

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.AchievementIcon
import br.com.lactarehub.domain.entity.AchievementStatus
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.DonationBadge
import br.com.lactarehub.presentation.component.HeroGradientBox
import br.com.lactarehub.presentation.component.TrackingTimeline

@Composable
fun JourneyCard(
    donor: Donor,
    schedule: CollectionSchedule?,
    modifier: Modifier = Modifier,
) {
    HeroGradientBox(modifier = modifier) {
        Text(
            text = "SUA JORNADA",
            style = AppTextStyles.overline.copy(color = AppColors.Surface.copy(alpha = 0.75f)),
        )
        Spacer(Modifier.height(AppSpacing.md))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${donor.completedDonations}",
                style = AppTextStyles.statValueOnDark.withSize(38.0),
            )
            Spacer(Modifier.width(AppSpacing.sm))
            Text(
                text = "doações realizadas",
                style = AppTextStyles.body.copy(color = AppColors.Surface.copy(alpha = 0.9f)),
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }
        Spacer(Modifier.height(AppSpacing.xl))
        Row(modifier = Modifier.fillMaxWidth()) {
            InnerStat(
                label = "Última doação",
                value = donor.daysSinceLastDonation
                    ?.let { Formatters.daysAgo(it) }
                    ?: "Nenhuma ainda",
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(AppSpacing.md))
            InnerStat(
                label = "Próximo agendamento",
                value = schedule?.let {
                    "${Formatters.weekdayAndDate(it.scheduledAt)} · ${it.scheduledAt.hour}h"
                } ?: "A marcar",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun InnerStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(AppColors.Surface.copy(alpha = 0.16f), AppRadius.input)
            .padding(AppSpacing.md),
    ) {
        Text(
            text = label,
            style = AppTextStyles.caption.copy(color = AppColors.Surface.copy(alpha = 0.8f)),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = AppTextStyles.label.withSize(14.5).copy(color = AppColors.Surface),
        )
    }
}

@Composable
fun AchievementsGrid(
    achievements: List<Achievement>,
    onClick: (Achievement) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.md),
    ) {
        achievements.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(AppSpacing.md)) {
                row.forEach { achievement ->
                    AchievementTile(
                        achievement = achievement,
                        onClick = onClick,
                        modifier = Modifier.weight(1f),
                    )
                }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun AchievementTile(
    achievement: Achievement,
    onClick: (Achievement) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLocked = achievement.status == AchievementStatus.BLOQUEADA
    val icon = when (achievement.icon) {
        AchievementIcon.GOTA -> AppIcons.BadgeDrop
        AchievementIcon.MEDALHA -> AppIcons.BadgeMedal
        AchievementIcon.ESTRELA -> AppIcons.BadgeStar
        AchievementIcon.CORACAO -> AppIcons.BadgeHeart
        AchievementIcon.FOLHA -> AppIcons.BadgeLeaf
        AchievementIcon.BRILHO -> AppIcons.BadgeSparkle
    }
    val labelColor = when (achievement.status) {
        AchievementStatus.CONQUISTADA -> AppColors.Primary
        AchievementStatus.EM_PROGRESSO -> AppColors.InkMuted
        AchievementStatus.BLOQUEADA -> AppColors.NavInactive
    }

    Column(
        modifier = modifier
            .height(132.dp)
            .clip(AppRadius.card)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.card)
            .clickable { onClick(achievement) }
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .then(
                    if (isLocked) {
                        Modifier.background(AppColors.BgApp, CircleShape)
                    } else {
                        Modifier.background(
                            brush = Brush.linearGradient(
                                AppColors.avatarGradient(achievement.gradientIndex),
                            ),
                            shape = CircleShape,
                        )
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (isLocked) AppIcons.Locked else icon,
                contentDescription = null,
                tint = if (isLocked) AppColors.NavInactive else AppColors.Surface,
                modifier = Modifier.size(22.dp),
            )
        }
        Spacer(Modifier.height(AppSpacing.sm))
        Text(
            text = achievement.title,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = AppTextStyles.badge.withSize(12.0).copy(
                color = if (isLocked) AppColors.InkMuted else AppColors.PrimaryDark,
            ),
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = achievement.progressLabel,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTextStyles.caption.withSize(11.0).copy(color = labelColor),
        )
    }
}

@Composable
fun ReferralCard(onInvite: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.TintBlue, AppRadius.largeCard)
            .padding(AppSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(AppColors.PinkStrong, RoundedCornerShape(AppRadius.md)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                AppIcons.GiftFilled,
                contentDescription = null,
                tint = AppColors.Surface,
                modifier = Modifier.size(25.dp),
            )
        }
        Spacer(Modifier.width(AppSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Indique uma amiga", style = AppTextStyles.cardTitleBlue.withSize(15.5))
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Cada pessoa que você convidar amplia a rede de cuidado.",
                style = AppTextStyles.caption,
            )
        }
        Spacer(Modifier.width(AppSpacing.sm))
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(AppColors.Accent)
                .clickable(onClick = onInvite),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                AppIcons.Send,
                contentDescription = "Convidar",
                tint = AppColors.Surface,
                modifier = Modifier.size(19.dp),
            )
        }
    }
}

@Composable
fun TrackingCard(donation: Donation, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppRadius.largeCard)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .clickable(onClick = onClick)
            .padding(AppSpacing.lg),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Doação #${donation.code}", style = AppTextStyles.caption)
                Spacer(Modifier.height(3.dp))
                Text(
                    text = "Coletada em ${Formatters.paddedDate(donation.collectedAt)}",
                    style = AppTextStyles.cardTitleBlue.withSize(15.5),
                )
            }
            Spacer(Modifier.width(AppSpacing.sm))
            DonationBadge(donation.status)
        }
        Spacer(Modifier.height(AppSpacing.xl))
        TrackingTimeline(steps = donation.timeline)
    }
}
