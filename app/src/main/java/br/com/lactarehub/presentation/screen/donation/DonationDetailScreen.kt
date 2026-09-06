package br.com.lactarehub.presentation.screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeBottomPadding
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.DonationBadge
import br.com.lactarehub.presentation.component.InfoNoteCard
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.SectionTitle
import br.com.lactarehub.presentation.component.StatTile
import br.com.lactarehub.presentation.component.TrackingTimeline
import br.com.lactarehub.presentation.viewmodel.DonationDetailViewModel

@Composable
fun DonationDetailScreen(
    donationCode: String,
    goBack: () -> Unit,
    viewModel: DonationDetailViewModel = viewModel(),
) {
    LaunchedEffect(donationCode) { viewModel.load(donationCode) }

    val donation = viewModel.donation

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(title = "Doação #$donationCode", onBack = goBack)

        if (donation == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingBox()
            }
            return@Column
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .safeBottomPadding()
                .padding(
                    start = AppSpacing.page,
                    top = AppSpacing.xl,
                    end = AppSpacing.page,
                    bottom = AppSpacing.section,
                ),
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "Coletada em ${Formatters.paddedDate(donation.collectedAt)}",
                    style = AppTextStyles.heroTitle.withSize(24.0),
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(AppSpacing.md))
                DonationBadge(donation.status)
            }

            Spacer(Modifier.height(AppSpacing.xl))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(AppColors.Surface, AppRadius.largeCard)
                    .border(1.dp, AppColors.Border, AppRadius.largeCard)
                    .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.xl),
            ) {
                StatTile(
                    value = Formatters.volume(donation.volumeMilliliters),
                    label = "volume doado",
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                )
                VerticalDivider(color = AppColors.Border)
                StatTile(
                    value = "${donation.timeline.size}",
                    label = "etapas de rastreio",
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(Modifier.height(AppSpacing.xl))
            InlineInfo(
                icon = AppIcons.Place,
                label = "Origem",
                value = donation.collectionPlace,
            )
            InlineInfo(
                icon = AppIcons.Hospital,
                label = "Destino",
                value = donation.destinationHospital,
            )

            Spacer(Modifier.height(AppSpacing.xl))
            SectionTitle(title = "Percurso do seu leite")
            Spacer(Modifier.height(AppSpacing.xl))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.Surface, AppRadius.largeCard)
                    .border(1.dp, AppColors.Border, AppRadius.largeCard)
                    .padding(AppSpacing.lg),
            ) {
                TrackingTimeline(steps = donation.timeline)
            }

            Spacer(Modifier.height(AppSpacing.xl))
            InfoNoteCard(
                icon = AppIcons.Science,
                message = "Todo lote é pasteurizado a 62,5 °C por trinta minutos e " +
                    "analisado antes de chegar à unidade neonatal.",
            )
        }
    }
}

@Composable
private fun InlineInfo(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = AppSpacing.md),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(icon, contentDescription = null, tint = AppColors.Primary, modifier = Modifier.size(19.dp))
        Spacer(Modifier.width(AppSpacing.md))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = AppTextStyles.caption)
            Spacer(Modifier.height(2.dp))
            Text(
                text = value,
                style = AppTextStyles.bodySmall.copy(
                    color = AppColors.Ink,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        }
    }
}
