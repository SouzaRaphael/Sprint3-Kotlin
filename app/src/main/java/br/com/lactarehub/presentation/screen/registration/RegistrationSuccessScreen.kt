package br.com.lactarehub.presentation.screen.registration

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeBottomPadding
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.PrimaryButton

/** Confirmação exibida ao concluir o cadastro. */
@Composable
fun RegistrationSuccessScreen(onEnterApp: () -> Unit) {
    val nextSteps = listOf(
        "Um enfermeiro do BLH mais próximo entra em contato em até 48 horas.",
        "A triagem é concluída por telefone ou WhatsApp, sem sair de casa.",
        "Depois disso você já pode agendar a sua primeira coleta.",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding()
            .safeBottomPadding()
            .padding(AppSpacing.page),
    ) {
        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(76.dp)
                .background(AppColors.SuccessBg, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                AppIcons.Check,
                contentDescription = null,
                tint = AppColors.SuccessFg,
                modifier = Modifier.size(40.dp),
            )
        }

        Spacer(Modifier.height(AppSpacing.xl))
        Text(text = "Cadastro enviado!", style = AppTextStyles.heroTitle.withSize(28.0))
        Spacer(Modifier.height(AppSpacing.md))
        Text(
            text = "Você acaba de dar o primeiro passo para alimentar bebês " +
                "prematuros. Veja o que acontece agora.",
            style = AppTextStyles.body,
        )

        Spacer(Modifier.height(AppSpacing.xl))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.TintBlue, AppRadius.card)
                .padding(AppSpacing.lg),
        ) {
            nextSteps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (index == nextSteps.lastIndex) 0.dp else 12.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(AppColors.Primary, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = AppTextStyles.badge.copy(color = AppColors.Surface),
                        )
                    }
                    Spacer(Modifier.width(AppSpacing.md))
                    Text(
                        text = step,
                        style = AppTextStyles.bodySmall.copy(color = AppColors.PrimaryDark),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))
        PrimaryButton(label = "Ir para a minha área", onClick = onEnterApp)
        Spacer(Modifier.height(AppSpacing.lg))
    }
}
