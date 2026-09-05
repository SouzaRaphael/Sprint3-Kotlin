package br.com.lactarehub.presentation.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.core.util.Formatters
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTextField
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.component.SectionTitle
import br.com.lactarehub.presentation.viewmodel.ScheduleViewModel

/**
 * Aba "Doar" — formulário de agendamento de uma nova coleta.
 *
 * Complementa as telas do protótipo, dando destino aos botões
 * "Nova coleta" e "Agendar nova coleta".
 */
@Composable
fun ScheduleCollectionScreen(
    viewModel: ScheduleViewModel,
    onScheduled: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val feedback = LocalAppFeedback.current

    if (viewModel.isLoading) {
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(title = "Agendar coleta")

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = AppSpacing.page,
                    top = AppSpacing.xl,
                    end = AppSpacing.page,
                    bottom = AppSpacing.section,
                ),
        ) {
            Text(
                text = "Vamos combinar a sua próxima doação",
                style = AppTextStyles.heroTitle.withSize(24.0),
            )
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = "Escolha como quer entregar o leite, o dia e a janela " +
                    "de horário que cabem na sua rotina.",
                style = AppTextStyles.body,
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            SectionTitle(title = "Como você prefere doar?")
            Spacer(Modifier.height(AppSpacing.lg))
            ModeSelector(selected = viewModel.mode, onSelected = viewModel::selectMode)

            if (viewModel.requiresPoint) {
                Spacer(Modifier.height(AppSpacing.lg))
                PointPicker(
                    points = viewModel.points,
                    selected = viewModel.selectedPoint,
                    onSelected = viewModel::selectPoint,
                )
            }

            Spacer(Modifier.height(AppSpacing.xxl))
            SectionTitle(title = "Escolha o dia")
            Spacer(Modifier.height(AppSpacing.lg))
            DateStrip(
                dates = viewModel.selectableDates,
                selected = viewModel.date,
                onSelected = viewModel::selectDate,
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            SectionTitle(title = "Janela de horário")
            Spacer(Modifier.height(AppSpacing.lg))
            TimeWindowGrid(
                windows = viewModel.availableWindows,
                selected = viewModel.timeWindow,
                onSelected = viewModel::selectTimeWindow,
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            AppTextField(
                label = "Observações (opcional)",
                hint = "Ex.: interfone com defeito, chamar no celular",
                value = viewModel.notes,
                onValueChange = viewModel::updateNotes,
                maxLines = 3,
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            PrimaryButton(
                label = "Confirmar agendamento",
                icon = AppIcons.EventAvailable,
                showTrailingIcon = false,
                isLoading = viewModel.isSubmitting,
                onClick = if (viewModel.canSubmit) {
                    {
                        viewModel.submit { schedule ->
                            feedback.success(
                                "Coleta agendada para " +
                                    "${Formatters.weekdayAndDate(schedule.scheduledAt)}, " +
                                    "${schedule.timeWindow}.",
                            )
                            onScheduled()
                        }
                    }
                } else {
                    null
                },
            )

            if (!viewModel.canSubmit) {
                Spacer(Modifier.height(AppSpacing.md))
                Text(
                    text = "Selecione dia e horário" +
                        (if (viewModel.requiresPoint) " e um ponto de coleta" else "") +
                        " para continuar.",
                    textAlign = TextAlign.Center,
                    style = AppTextStyles.caption,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
