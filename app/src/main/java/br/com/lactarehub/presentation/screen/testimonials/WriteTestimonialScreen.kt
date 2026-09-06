package br.com.lactarehub.presentation.screen.testimonials

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.core.theme.safeBottomPadding
import br.com.lactarehub.core.theme.safeTopPadding
import br.com.lactarehub.core.theme.withSize
import br.com.lactarehub.domain.entity.TestimonialType
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTextField
import br.com.lactarehub.presentation.component.AppTopBar
import br.com.lactarehub.presentation.component.InfoNoteCard
import br.com.lactarehub.presentation.component.LoadingBox
import br.com.lactarehub.presentation.component.LocalAppFeedback
import br.com.lactarehub.presentation.component.PrimaryButton
import br.com.lactarehub.presentation.component.TestimonialBadge
import br.com.lactarehub.presentation.viewmodel.WriteTestimonialViewModel

@Composable
fun WriteTestimonialScreen(
    goBack: () -> Unit,
    viewModel: WriteTestimonialViewModel = viewModel(),
) {
    val feedback = LocalAppFeedback.current
    val donor = viewModel.donor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgApp)
            .safeTopPadding(),
    ) {
        AppTopBar(title = "Escrever depoimento", onBack = goBack)

        if (donor == null) {
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
            Text(
                text = "Conte como foi para você",
                style = AppTextStyles.heroTitle.withSize(24.0),
            )
            Spacer(Modifier.height(AppSpacing.sm))
            Text(
                text = "Seu relato aparece na lista pública assinado como " +
                    "${donor.fullName}, de ${donor.cityAndState}.",
                style = AppTextStyles.body,
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            Text(text = "Como você se descreve hoje?", style = AppTextStyles.label)
            Spacer(Modifier.height(AppSpacing.md))
            Row {
                TestimonialType.entries.forEach { type ->
                    TypeChoice(
                        type = type,
                        isSelected = type == viewModel.type,
                        onClick = { viewModel.selectType(type) },
                        modifier = Modifier.padding(end = AppSpacing.sm),
                    )
                }
            }

            Spacer(Modifier.height(AppSpacing.xl))
            AppTextField(
                label = "Seu depoimento",
                hint = "O que a doação mudou na sua rotina?",
                value = viewModel.message,
                onValueChange = viewModel::onMessageChange,
                maxLines = 6,
                capitalization = KeyboardCapitalization.Sentences,
                errorMessage = viewModel.messageError,
            )

            Spacer(Modifier.height(AppSpacing.lg))
            InfoNoteCard(
                icon = AppIcons.Shield,
                message = "Nenhum dado de saúde é publicado. Você pode pedir a " +
                    "remoção do depoimento a qualquer momento.",
            )

            Spacer(Modifier.height(AppSpacing.xxl))
            PrimaryButton(
                label = "Publicar depoimento",
                icon = AppIcons.Send,
                showTrailingIcon = false,
                isLoading = viewModel.isSubmitting,
                onClick = {
                    viewModel.submit {
                        feedback.success(
                            "Depoimento publicado! Obrigado por inspirar outras doadoras.",
                        )
                        goBack()
                    }
                },
            )
        }
    }
}

@Composable
private fun TypeChoice(
    type: TestimonialType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .alpha(if (isSelected) 1f else 0.45f)
            .border(
                width = 1.6.dp,
                color = if (isSelected) AppColors.Accent else Color.Transparent,
                shape = AppRadius.pillShape,
            )
            .clickable(onClick = onClick)
            .padding(2.dp),
    ) {
        TestimonialBadge(type)
    }
}
