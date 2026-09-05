package br.com.lactarehub.presentation.screen.registration

import androidx.compose.foundation.background
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
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.viewmodel.RegistrationStep
import br.com.lactarehub.presentation.viewmodel.RegistrationViewModel

/** Tela 03 do protótipo — cadastro da doadora em quatro etapas. */
@Composable
fun RegistrationScreen(
    onCompleted: () -> Unit,
    goBack: () -> Unit,
    viewModel: RegistrationViewModel = viewModel(),
) {
    val feedback = LocalAppFeedback.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(
            title = "Cadastro",
            // Voltar recua uma etapa; na primeira, sai do cadastro.
            onBack = { if (viewModel.isFirstStep) goBack() else viewModel.goToPreviousStep() },
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(
                    start = AppSpacing.page,
                    top = AppSpacing.xl,
                    end = AppSpacing.page,
                    bottom = AppSpacing.xl,
                ),
        ) {
            RegistrationProgress(
                currentStep = viewModel.stepNumber,
                totalSteps = viewModel.totalSteps,
            )
            Spacer(Modifier.height(AppSpacing.lg))
            Text(text = viewModel.step.title, style = AppTextStyles.heroTitle.withSize(26.0))
            Spacer(Modifier.height(AppSpacing.sm))
            Text(text = viewModel.step.subtitle, style = AppTextStyles.body)
            Spacer(Modifier.height(AppSpacing.xxl))

            when (viewModel.step) {
                RegistrationStep.SOBRE_VOCE -> StepAboutYou(viewModel)
                RegistrationStep.ENDERECO -> StepAddress(viewModel)
                RegistrationStep.SAUDE -> StepHealth(viewModel)
                RegistrationStep.REVISAO -> StepReview(viewModel)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.BgApp)
                .safeBottomPadding(),
        ) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AppColors.Border),
            )
            PrimaryButton(
                label = if (viewModel.isLastStep) "Finalizar cadastro" else "Continuar",
                isLoading = viewModel.isSubmitting,
                showTrailingIcon = !viewModel.isLastStep,
                icon = if (viewModel.isLastStep) AppIcons.Check else null,
                onClick = {
                    val readyToSubmit = viewModel.validateAndAdvance()
                    if (!readyToSubmit) return@PrimaryButton

                    if (!viewModel.draft.acceptedTerms) {
                        feedback.error("Aceite os termos para concluir o seu cadastro.")
                        return@PrimaryButton
                    }
                    viewModel.submit(onCompleted)
                },
                modifier = Modifier.padding(
                    start = AppSpacing.page,
                    top = AppSpacing.md,
                    end = AppSpacing.page,
                    bottom = AppSpacing.xl,
                ),
            )
        }
    }
}
