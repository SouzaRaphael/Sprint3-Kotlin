package br.com.lactarehub.presentation.screen.registration

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.lactarehub.core.theme.AppColors
import br.com.lactarehub.core.theme.AppRadius
import br.com.lactarehub.core.theme.AppSpacing
import br.com.lactarehub.core.theme.AppTextStyles
import br.com.lactarehub.domain.entity.RegistrationDraft
import br.com.lactarehub.presentation.component.AppIcons
import br.com.lactarehub.presentation.component.AppTextField
import br.com.lactarehub.presentation.viewmodel.RegistrationField
import br.com.lactarehub.presentation.viewmodel.RegistrationViewModel

/** Barra segmentada do topo do cadastro, com o texto "Etapa X de Y". */
@Composable
fun RegistrationProgress(currentStep: Int, totalSteps: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            repeat(totalSteps) { index ->
                val segmentColor by animateColorAsState(
                    targetValue = if (index < currentStep) AppColors.Primary else AppColors.TintBlue,
                    animationSpec = tween(250),
                    label = "progress-segment",
                )
                Spacer(
                    Modifier
                        .weight(1f)
                        .height(5.dp)
                        .background(color = segmentColor, shape = RoundedCornerShape(3.dp)),
                )
                if (index < totalSteps - 1) Spacer(Modifier.width(AppSpacing.sm))
            }
        }
        Spacer(Modifier.height(AppSpacing.md))
        Text(
            text = buildAnnotatedString {
                append("Etapa ")
                withStyle(SpanStyle(color = AppColors.Primary, fontWeight = FontWeight.Bold)) {
                    append("$currentStep")
                }
                append(" de $totalSteps")
            },
            style = AppTextStyles.bodySmall,
        )
    }
}

/** Etapa 1 — dados pessoais, fiel à captura do protótipo. */
@Composable
fun StepAboutYou(viewModel: RegistrationViewModel, modifier: Modifier = Modifier) {
    val draft = viewModel.draft

    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            label = "Nome completo",
            hint = "Como você se chama?",
            value = draft.fullName,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.FULL_NAME) { it.copy(fullName = value) }
            },
            capitalization = KeyboardCapitalization.Words,
            errorMessage = viewModel.errors[RegistrationField.FULL_NAME],
        )
        Spacer(Modifier.height(AppSpacing.lg))
        AppTextField(
            label = "E-mail",
            hint = "seu@email.com",
            value = draft.email,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.EMAIL) { it.copy(email = value) }
            },
            keyboardType = KeyboardType.Email,
            errorMessage = viewModel.errors[RegistrationField.EMAIL],
        )
        Spacer(Modifier.height(AppSpacing.lg))
        AppTextField(
            label = "Telefone / WhatsApp",
            hint = "(11) 99999-9999",
            value = draft.phone,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.PHONE) { it.copy(phone = value) }
            },
            keyboardType = KeyboardType.Phone,
            errorMessage = viewModel.errors[RegistrationField.PHONE],
        )
        Spacer(Modifier.height(AppSpacing.lg))
        AppTextField(
            label = "Data de nascimento",
            hint = "dd/mm/aaaa",
            value = draft.birthDate,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.BIRTH_DATE) { it.copy(birthDate = value) }
            },
            keyboardType = KeyboardType.Number,
            errorMessage = viewModel.errors[RegistrationField.BIRTH_DATE],
            suffix = {
                Icon(
                    AppIcons.Calendar,
                    contentDescription = null,
                    tint = AppColors.NavInactive,
                    modifier = Modifier.size(18.dp),
                )
            },
        )
    }
}

/** Etapa 2 — endereço, que define o BLH mais próximo. */
@Composable
fun StepAddress(viewModel: RegistrationViewModel, modifier: Modifier = Modifier) {
    val draft = viewModel.draft

    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            label = "CEP",
            hint = "04101-300",
            value = draft.zipCode,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.ZIP_CODE) { it.copy(zipCode = value) }
            },
            keyboardType = KeyboardType.Number,
            errorMessage = viewModel.errors[RegistrationField.ZIP_CODE],
        )
        Spacer(Modifier.height(AppSpacing.lg))
        AppTextField(
            label = "Rua",
            hint = "Nome da rua ou avenida",
            value = draft.street,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.STREET) { it.copy(street = value) }
            },
            capitalization = KeyboardCapitalization.Words,
            errorMessage = viewModel.errors[RegistrationField.STREET],
        )
        Spacer(Modifier.height(AppSpacing.lg))
        Row(verticalAlignment = Alignment.Top) {
            AppTextField(
                label = "Número",
                hint = "1492",
                value = draft.number,
                onValueChange = { value ->
                    viewModel.updateField(RegistrationField.NUMBER) { it.copy(number = value) }
                },
                keyboardType = KeyboardType.Number,
                errorMessage = viewModel.errors[RegistrationField.NUMBER],
                modifier = Modifier.weight(2f),
            )
            Spacer(Modifier.width(AppSpacing.md))
            AppTextField(
                label = "Bairro",
                hint = "Vila Mariana",
                value = draft.neighborhood,
                onValueChange = { value ->
                    viewModel.updateField(RegistrationField.NEIGHBORHOOD) {
                        it.copy(neighborhood = value)
                    }
                },
                capitalization = KeyboardCapitalization.Words,
                errorMessage = viewModel.errors[RegistrationField.NEIGHBORHOOD],
                modifier = Modifier.weight(3f),
            )
        }
        Spacer(Modifier.height(AppSpacing.lg))
        Row(verticalAlignment = Alignment.Top) {
            AppTextField(
                label = "Cidade",
                hint = "São Paulo",
                value = draft.city,
                onValueChange = { value ->
                    viewModel.updateField(RegistrationField.CITY) { it.copy(city = value) }
                },
                capitalization = KeyboardCapitalization.Words,
                errorMessage = viewModel.errors[RegistrationField.CITY],
                modifier = Modifier.weight(3f),
            )
            Spacer(Modifier.width(AppSpacing.md))
            AppTextField(
                label = "UF",
                hint = "SP",
                value = draft.state,
                onValueChange = { value ->
                    viewModel.updateField(RegistrationField.STATE) {
                        it.copy(state = value.uppercase())
                    }
                },
                capitalization = KeyboardCapitalization.Characters,
                errorMessage = viewModel.errors[RegistrationField.STATE],
                modifier = Modifier.weight(1f),
            )
        }
    }
}

/** Etapa 3 — triagem de saúde. */
@Composable
fun StepHealth(viewModel: RegistrationViewModel, modifier: Modifier = Modifier) {
    val draft = viewModel.draft

    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            label = "Idade do bebê (em meses)",
            hint = "4",
            value = draft.babyAgeMonths,
            onValueChange = { value ->
                viewModel.updateField(RegistrationField.BABY_AGE) { it.copy(babyAgeMonths = value) }
            },
            keyboardType = KeyboardType.Number,
            errorMessage = viewModel.errors[RegistrationField.BABY_AGE],
        )
        Spacer(Modifier.height(AppSpacing.lg))
        ToggleRow(
            title = "Estou amamentando atualmente",
            description = "Requisito para participar da rede de doação.",
            value = draft.isBreastfeeding,
            onValueChange = { value -> viewModel.updateDraft { it.copy(isBreastfeeding = value) } },
        )
        Spacer(Modifier.height(AppSpacing.md))
        ToggleRow(
            title = "Uso medicamento contínuo",
            description = "A maior parte dos remédios comuns é compatível.",
            value = draft.takesMedication,
            onValueChange = { value -> viewModel.updateDraft { it.copy(takesMedication = value) } },
        )
        if (draft.takesMedication) {
            Spacer(Modifier.height(AppSpacing.lg))
            AppTextField(
                label = "Quais medicamentos?",
                hint = "Liste os medicamentos em uso",
                value = draft.medicationNotes,
                onValueChange = { value ->
                    viewModel.updateField(RegistrationField.MEDICATION) {
                        it.copy(medicationNotes = value)
                    }
                },
                maxLines = 3,
                errorMessage = viewModel.errors[RegistrationField.MEDICATION],
            )
        }
    }
}

/** Linha com título, descrição e interruptor. */
@Composable
private fun ToggleRow(
    title: String,
    description: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface, AppRadius.card)
            .border(1.dp, AppColors.BorderInput, AppRadius.card)
            .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = AppTextStyles.label)
            Spacer(Modifier.height(4.dp))
            Text(text = description, style = AppTextStyles.caption)
        }
        Spacer(Modifier.width(AppSpacing.md))
        Switch(
            checked = value,
            onCheckedChange = onValueChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppColors.Surface,
                checkedTrackColor = AppColors.Primary,
                checkedBorderColor = AppColors.Primary,
                uncheckedThumbColor = AppColors.NavInactive,
                uncheckedTrackColor = AppColors.Surface,
                uncheckedBorderColor = AppColors.Border,
            ),
        )
    }
}

/** Etapa 4 — revisão dos dados e aceite dos termos. */
@Composable
fun StepReview(viewModel: RegistrationViewModel, modifier: Modifier = Modifier) {
    val draft = viewModel.draft

    Column(modifier = modifier.fillMaxWidth()) {
        ReviewCard(
            title = "Sobre você",
            rows = listOf(
                "Nome" to draft.fullName,
                "E-mail" to draft.email,
                "Telefone" to draft.phone,
                "Nascimento" to draft.birthDate,
            ),
        )
        Spacer(Modifier.height(AppSpacing.md))
        ReviewCard(
            title = "Endereço",
            rows = listOf(
                "CEP" to draft.zipCode,
                "Endereço" to draft.formattedAddress,
            ),
        )
        Spacer(Modifier.height(AppSpacing.md))
        ReviewCard(
            title = "Saúde e triagem",
            rows = listOf(
                "Idade do bebê" to "${draft.babyAgeMonths} meses",
                "Amamentando" to if (draft.isBreastfeeding) "Sim" else "Não",
                "Medicamento contínuo" to medicationSummary(draft),
            ),
        )
        Spacer(Modifier.height(AppSpacing.xl))
        TermsCheckbox(
            value = draft.acceptedTerms,
            onValueChange = { value -> viewModel.updateDraft { it.copy(acceptedTerms = value) } },
        )
    }
}

private fun medicationSummary(draft: RegistrationDraft): String = when {
    !draft.takesMedication -> "Não"
    draft.medicationNotes.isEmpty() -> "Sim"
    else -> draft.medicationNotes
}

@Composable
private fun ReviewCard(title: String, rows: List<Pair<String, String>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface, AppRadius.card)
            .border(1.dp, AppColors.Border, AppRadius.card)
            .padding(AppSpacing.lg),
    ) {
        Text(text = title, style = AppTextStyles.cardTitleBlue)
        Spacer(Modifier.height(AppSpacing.md))
        rows.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Text(text = label, style = AppTextStyles.caption, modifier = Modifier.width(120.dp))
                Text(
                    text = value.ifEmpty { "—" },
                    style = AppTextStyles.bodySmall.copy(
                        color = AppColors.Ink,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun TermsCheckbox(value: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onValueChange(!value) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Checkbox(
            checked = value,
            onCheckedChange = { checked -> onValueChange(checked) },
            colors = CheckboxDefaults.colors(checkedColor = AppColors.Primary),
        )
        Spacer(Modifier.width(AppSpacing.sm))
        Text(
            text = "Autorizo o contato da equipe do banco de leite e concordo " +
                "com a política de privacidade e o tratamento dos meus dados " +
                "conforme a LGPD.",
            style = AppTextStyles.bodySmall,
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}
