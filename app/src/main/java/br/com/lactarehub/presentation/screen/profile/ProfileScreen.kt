package br.com.lactarehub.presentation.screen.profile

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.AvatarCircle
import br.com.lactarehub.presentation.component.EmptyStateCard
import br.com.lactarehub.presentation.component.InfoNoteCard
import br.com.lactarehub.presentation.component.InfoRow
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.SecondaryButton
import br.com.lactarehub.presentation.component.StatTile
import br.com.lactarehub.presentation.component.StatusBadge
import br.com.lactarehub.presentation.viewmodel.ProfileViewModel

/**
 * Perfil da doadora: dados do cadastro e a coleta agendada no momento.
 *
 * Carrega os dados ao abrir, então sempre reflete o último agendamento —
 * inclusive um acabado de registrar ou alterar.
 */
@Composable
fun ProfileScreen(
    goBack: () -> Unit,
    onScheduleCollection: () -> Unit,
    onSignedOut: () -> Unit,
    viewModel: ProfileViewModel = viewModel(),
) {
    val donor = viewModel.donor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(title = "Meu perfil", onBack = goBack)

        if (viewModel.isLoading || donor == null) {
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
            ProfileHeader(donor)

            Spacer(Modifier.height(AppSpacing.xxl))
            NextCollectionSection(
                schedule = viewModel.schedule,
                onSchedule = onScheduleCollection,
            )

            Spacer(Modifier.height(AppSpacing.lg))
            ProfileCard(title = "Contato") {
                InfoRow(icon = AppIcons.Email, label = "E-mail", value = donor.email)
                InfoRow(icon = AppIcons.Phone, label = "Telefone / WhatsApp", value = donor.phone)
                InfoRow(
                    icon = AppIcons.Cake,
                    label = "Data de nascimento",
                    value = donor.birthDate,
                    bottomSpacing = 0.dp,
                )
            }

            Spacer(Modifier.height(AppSpacing.lg))
            ProfileCard(title = "Endereço") {
                InfoRow(icon = AppIcons.Mailbox, label = "CEP", value = donor.zipCode)
                InfoRow(
                    icon = AppIcons.ModeHome,
                    label = "Logradouro",
                    value = "${donor.street}, ${donor.number}",
                )
                InfoRow(
                    icon = AppIcons.Place,
                    label = "Bairro e cidade",
                    value = "${donor.neighborhood} — ${donor.cityAndState}",
                    bottomSpacing = 0.dp,
                )
            }

            Spacer(Modifier.height(AppSpacing.lg))
            ProfileCard(title = "Triagem") {
                InfoRow(
                    icon = AppIcons.Baby,
                    label = "Idade do bebê",
                    value = if (donor.babyAgeMonths.isEmpty()) "" else "${donor.babyAgeMonths} meses",
                )
                InfoRow(
                    icon = AppIcons.Breastfeeding,
                    label = "Amamentando",
                    value = if (donor.isBreastfeeding) "Sim" else "Não",
                )
                InfoRow(
                    icon = AppIcons.Medication,
                    label = "Medicamento contínuo",
                    value = when {
                        !donor.takesMedication -> "Não"
                        donor.medicationNotes.isEmpty() -> "Sim"
                        else -> donor.medicationNotes
                    },
                    bottomSpacing = 0.dp,
                )
            }

            Spacer(Modifier.height(AppSpacing.lg))
            JourneySummary(donor)

            Spacer(Modifier.height(AppSpacing.lg))
            InfoNoteCard(
                icon = AppIcons.Lock,
                message = "Seus dados de saúde ficam visíveis apenas para você e " +
                    "para a equipe do banco de leite.",
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            SecondaryButton(
                label = if (viewModel.isSigningOut) "Saindo…" else "Sair da conta",
                icon = AppIcons.Logout,
                foregroundColor = AppColors.InkMuted,
                onClick = if (viewModel.isSigningOut) {
                    null
                } else {
                    { viewModel.signOut(onSignedOut) }
                },
            )
        }
    }
}

/** Card da coleta marcada, ou o convite para marcar a primeira. */
@Composable
private fun NextCollectionSection(schedule: CollectionSchedule?, onSchedule: () -> Unit) {
    if (schedule == null) {
        EmptyStateCard(
            icon = AppIcons.EventAvailable,
            title = "Nenhuma coleta agendada",
            message = "Marque a próxima coleta para acompanhar tudo por aqui.",
            actionLabel = "Agendar coleta",
            onAction = onSchedule,
        )
        return
    }

    ProfileCard(
        title = "Próxima coleta",
        trailing = {
            if (schedule.isConfirmed) {
                StatusBadge(
                    label = "Confirmada",
                    background = AppColors.SuccessBg,
                    foreground = AppColors.SuccessFg,
                    icon = AppIcons.CheckCircleOutline,
                )
            } else {
                StatusBadge(
                    label = "Aguardando confirmação",
                    background = AppColors.TintBlue,
                    foreground = AppColors.Primary,
                    icon = AppIcons.Clock,
                )
            }
        },
    ) {
        InfoRow(
            icon = AppIcons.Event,
            label = "Data",
            value = "${Formatters.weekdayAndDate(schedule.scheduledAt)} de " +
                "${schedule.scheduledAt.year}",
        )
        InfoRow(icon = AppIcons.Time, label = "Janela de horário", value = schedule.timeWindow)
        InfoRow(icon = AppIcons.Shipping, label = "Modalidade", value = schedule.mode.label)
        InfoRow(
            icon = AppIcons.Place,
            label = "Local",
            value = schedule.place,
            bottomSpacing = if (schedule.notes.isBlank()) 0.dp else AppSpacing.lg,
        )
        if (schedule.notes.isNotBlank()) {
            InfoRow(
                icon = AppIcons.Note,
                label = "Observações",
                value = schedule.notes,
                bottomSpacing = 0.dp,
            )
        }
    }
}

/** Avatar, nome e situação da jornada. */
@Composable
private fun ProfileHeader(donor: Donor) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AvatarCircle(
            name = donor.fullName,
            gradientIndex = donor.avatarGradientIndex,
            size = 84.dp,
        )
        Spacer(Modifier.height(AppSpacing.lg))
        Text(
            text = donor.fullName,
            textAlign = TextAlign.Center,
            style = AppTextStyles.heroTitle.withSize(23.0),
        )
        Spacer(Modifier.height(4.dp))
        Text(text = donor.cityAndState, style = AppTextStyles.bodySmall)
        Spacer(Modifier.height(AppSpacing.md))
        StatusBadge(
            label = if (donor.isStartingJourney) "Cadastro concluído" else "Doadora ativa",
            background = if (donor.isStartingJourney) AppColors.TintBlue else AppColors.SuccessBg,
            foreground = if (donor.isStartingJourney) AppColors.Primary else AppColors.SuccessFg,
            icon = if (donor.isStartingJourney) {
                AppIcons.CheckCircleOutline
            } else {
                AppIcons.HeartFilled
            },
        )
    }
}

/** Card branco com título, selo opcional e uma sequência de [InfoRow]. */
@Composable
private fun ProfileCard(
    title: String,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface, AppRadius.largeCard)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .padding(AppSpacing.lg),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = title,
                style = AppTextStyles.cardTitleBlue,
                modifier = Modifier.weight(1f),
            )
            if (trailing != null) {
                Spacer(Modifier.width(AppSpacing.sm))
                trailing()
            }
        }
        Spacer(Modifier.height(AppSpacing.lg))
        content()
    }
}

/** Números da jornada, no mesmo formato do card de impacto da home. */
@Composable
private fun JourneySummary(donor: Donor) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(AppColors.Surface, AppRadius.largeCard)
            .border(1.dp, AppColors.Border, AppRadius.largeCard)
            .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.xl),
    ) {
        StatTile(
            value = "${donor.completedDonations}",
            label = "doações",
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        )
        VerticalDivider(color = AppColors.Border)
        StatTile(
            value = Formatters.liters(donor.donatedMilliliters),
            label = "doados",
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        )
        VerticalDivider(color = AppColors.Border)
        StatTile(
            value = "~${donor.babiesReached}",
            label = "bebês alcançados",
            valueColor = AppColors.PinkStrong,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        )
    }
}
