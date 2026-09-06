package br.com.lactarehub.presentation.screen.donorhome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.cardShadow
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.DonationBadge
import br.com.lactarehub.presentation.component.HeroGradientBox

@Composable
fun NextCollectionCard(
    schedule: CollectionSchedule,
    isConfirming: Boolean,
    onConfirm: () -> Unit,
    onReschedule: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HeroGradientBox(modifier = modifier) {
        Row(verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "PRÓXIMA COLETA",
                    style = AppTextStyles.overline.copy(
                        color = AppColors.Surface.copy(alpha = 0.75f),
                    ),
                )
                Spacer(Modifier.height(AppSpacing.md))
                Text(
                    text = "${Formatters.weekdayAndDate(schedule.scheduledAt)} · " +
                        "${schedule.scheduledAt.hour}h",
                    style = AppTextStyles.statValueOnDark.withSize(23.0),
                )
                Spacer(Modifier.height(AppSpacing.sm))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        AppIcons.TabPoints,
                        contentDescription = null,
                        tint = AppColors.Surface.copy(alpha = 0.85f),
                        modifier = Modifier.size(15.dp),
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = schedule.summary,
                        style = AppTextStyles.bodySmall.copy(
                            color = AppColors.Surface.copy(alpha = 0.9f),
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Spacer(Modifier.width(AppSpacing.md))
            DateBadge(schedule)
        }

        Spacer(Modifier.height(AppSpacing.xl))
        Row(modifier = Modifier.fillMaxWidth()) {
            CardAction(
                label = if (schedule.isConfirmed) "Confirmada" else "Confirmar",
                icon = if (schedule.isConfirmed) AppIcons.Check else null,
                isLoading = isConfirming,
                isPrimary = true,
                onClick = if (schedule.isConfirmed) null else onConfirm,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(AppSpacing.md))
            CardAction(
                label = "Reagendar",
                isPrimary = false,
                onClick = onReschedule,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun DateBadge(schedule: CollectionSchedule) {
    Column(
        modifier = Modifier
            .width(56.dp)
            .background(AppColors.Surface.copy(alpha = 0.18f), AppRadius.input)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = Formatters.monthBadge(schedule.scheduledAt),
            style = AppTextStyles.badge.withSize(10.0).copy(
                color = AppColors.Surface.copy(alpha = 0.85f),
            ),
        )
        Text(
            text = schedule.scheduledAt.dayOfMonth.toString().padStart(2, '0'),
            style = AppTextStyles.statValueOnDark.withSize(22.0),
        )
    }
}

@Composable
private fun CardAction(
    label: String,
    isPrimary: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isLoading: Boolean = false,
) {
    val background = if (isPrimary) AppColors.Surface else AppColors.Surface.copy(alpha = 0.22f)
    val foreground = if (isPrimary) AppColors.PrimaryDark else AppColors.Surface

    Box(
        modifier = modifier
            .height(46.dp)
            .clip(AppRadius.pillShape)
            .background(background)
            .clickable(enabled = onClick != null && !isLoading) { onClick?.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.2.dp,
                color = foreground,
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = foreground, modifier = Modifier.size(17.dp))
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    text = label,
                    style = AppTextStyles.label.withSize(14.5).copy(color = foreground),
                )
            }
        }
    }
}

data class QuickAction(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit,
    val isHighlighted: Boolean = false,
)

@Composable
fun QuickActionsRow(actions: List<QuickAction>, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        actions.forEach { action ->
            QuickActionButton(action = action, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun QuickActionButton(action: QuickAction, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(AppSpacing.lg))
            .clickable(onClick = action.onClick)
            .padding(vertical = AppSpacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(
                    color = if (action.isHighlighted) AppColors.Accent else AppColors.TintBlue,
                    shape = RoundedCornerShape(AppSpacing.lg),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                action.icon,
                contentDescription = null,
                tint = if (action.isHighlighted) AppColors.Surface else AppColors.Primary,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(Modifier.height(AppSpacing.sm))
        Text(
            text = action.label,
            textAlign = TextAlign.Center,
            style = AppTextStyles.caption.copy(
                color = AppColors.PrimaryDark,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            ),
        )
    }
}

@Composable
fun ImpactSummaryCard(
    donor: Donor,
    onOpenAchievements: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val starting = donor.isStartingJourney
    val streakTitle = if (starting) {
        "Sua jornada começa agora"
    } else {
        "${donor.streakWeeks} semanas seguidas doando"
    }
    val streakSubtitle = if (starting) {
        "A primeira doação libera a primeira medalha."
    } else {
        "Falta ${donor.donationsToNextBadge} doação para a próxima medalha."
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .cardShadow(AppRadius.largeCard)
            .clip(AppRadius.largeCard)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.largeCard),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.xl),
        ) {
            br.com.lactarehub.presentation.component.StatTile(
                value = "${donor.completedDonations}",
                label = "doações",
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(color = AppColors.Border)
            br.com.lactarehub.presentation.component.StatTile(
                value = Formatters.liters(donor.donatedMilliliters),
                label = "doados",
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(color = AppColors.Border)
            br.com.lactarehub.presentation.component.StatTile(
                value = "~${donor.babiesReached}",
                label = "bebês alcançados",
                valueColor = AppColors.PinkStrong,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(AppColors.Border),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onOpenAchievements)
                .padding(AppSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (starting) AppColors.TintBlue else AppColors.PinkBg,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (starting) AppIcons.Rocket else AppIcons.Fire,
                    contentDescription = null,
                    tint = if (starting) AppColors.Primary else AppColors.PinkFg,
                    modifier = Modifier.size(20.dp),
                )
            }
            Spacer(Modifier.width(AppSpacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = streakTitle, style = AppTextStyles.label)
                Spacer(Modifier.height(3.dp))
                Text(text = streakSubtitle, style = AppTextStyles.caption)
            }
            Icon(
                AppIcons.Forward,
                contentDescription = null,
                tint = AppColors.NavInactive,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

@Composable
fun DonationPreviewCard(
    donation: Donation,
    onClick: (Donation) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppRadius.largeCard)
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .clickable { onClick(donation) }
            .padding(AppSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Doação #${donation.code}", style = AppTextStyles.caption)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Coletada em ${Formatters.paddedDate(donation.collectedAt)} · " +
                    Formatters.volume(donation.volumeMilliliters),
                style = AppTextStyles.cardTitleBlue.withSize(15.0),
            )
        }
        Spacer(Modifier.width(AppSpacing.sm))
        DonationBadge(donation.status)
    }
}

@Composable
fun TeamMessageCard(
    donorFirstName: String,
    hospital: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.TintBlue, AppRadius.largeCard)
            .padding(AppSpacing.lg),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(AppColors.Primary, RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                AppIcons.HeartFilled,
                contentDescription = null,
                tint = AppColors.Surface,
                modifier = Modifier.size(18.dp),
            )
        }
        Spacer(Modifier.width(AppSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Da equipe Lactare", style = AppTextStyles.label)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "$donorFirstName, sua última doação ajudou um bebê na " +
                    "$hospital. Cada gota conta.",
                style = AppTextStyles.bodySmall.copy(color = AppColors.PrimaryDark),
            )
        }
    }
}

@Composable
fun Greeting(name: String, gradientIndex: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        br.com.lactarehub.presentation.component.AvatarCircle(
            name = name,
            gradientIndex = gradientIndex,
            size = 42.dp,
        )
        Spacer(Modifier.width(AppSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Bom dia,", style = AppTextStyles.caption)
            Text(text = name, style = AppTextStyles.cardTitleBlue.withSize(17.0))
        }
        Box {
            Icon(
                AppIcons.Notifications,
                contentDescription = "Notificações",
                tint = AppColors.PrimaryDark,
                modifier = Modifier.size(25.dp),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(8.dp)
                    .background(AppColors.PinkStrong, CircleShape),
            )
        }
    }
}

val carouselSpacing: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(AppSpacing.md)
