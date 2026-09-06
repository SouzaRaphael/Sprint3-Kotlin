package br.com.lactarehub.presentation.screen.points

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import br.com.lactarehub.domain.entity.CollectionPointType
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.DotStatusLabel
import br.com.lactarehub.presentation.component.InfoNoteCard
import br.com.lactarehub.presentation.component.InfoRow
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.component.SecondaryButton
import br.com.lactarehub.presentation.viewmodel.CollectionPointDetailViewModel

@Composable
fun CollectionPointDetailScreen(
    pointId: String,
    goBack: () -> Unit,
    viewModel: CollectionPointDetailViewModel = viewModel(),
) {
    val feedback = LocalAppFeedback.current

    LaunchedEffect(pointId) { viewModel.load(pointId) }

    val point = viewModel.point

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(title = point?.type?.label ?: "Ponto de coleta", onBack = goBack)

        if (point == null) {
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
                    bottom = AppSpacing.xl,
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(AppColors.CoverBlue, AppColors.Accent),
                        ),
                        shape = AppRadius.largeCard,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    AppIcons.Drop,
                    contentDescription = null,
                    tint = AppColors.Surface,
                    modifier = Modifier.size(52.dp),
                )
            }

            Spacer(Modifier.height(AppSpacing.xl))
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = point.name,
                    style = AppTextStyles.heroTitle.withSize(24.0),
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(AppSpacing.md))
                DotStatusLabel(
                    label = if (point.isOpenNow) "Aberto" else "Fechado",
                    color = if (point.isOpenNow) AppColors.SuccessFg else AppColors.NavInactive,
                )
            }

            Spacer(Modifier.height(AppSpacing.xl))
            InfoRow(icon = AppIcons.Place, label = "Endereço", value = point.address)
            InfoRow(icon = AppIcons.Clock, label = "Funcionamento", value = point.openingHours)
            InfoRow(
                icon = AppIcons.NearMe,
                label = "Distância",
                value = if (point.type == CollectionPointType.COLETA_DOMICILIAR) {
                    "Atendimento no seu endereço"
                } else {
                    "${Formatters.oneDecimal(point.distanceKm)} km de você"
                },
            )
            InfoRow(icon = AppIcons.Call, label = "Telefone", value = point.phone)

            Spacer(Modifier.height(AppSpacing.xl))
            InfoNoteCard(
                icon = AppIcons.InfoOutline,
                message = "Leve o leite congelado em caixa térmica e apresente o " +
                    "seu cadastro do Lactare na recepção.",
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            PrimaryButton(
                label = "Traçar rota",
                icon = AppIcons.Route2,
                showTrailingIcon = false,
                onClick = {
                    feedback.info("Rota até ${point.name} aberta no aplicativo de mapas.")
                },
            )
            Spacer(Modifier.height(AppSpacing.md))
            SecondaryButton(
                label = "Ligar para o ponto",
                icon = AppIcons.Call,
                onClick = { feedback.info("Ligando para ${point.phone}.") },
            )
        }
    }
}
