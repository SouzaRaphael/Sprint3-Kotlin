package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.RegistrationDraft
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

enum class RegistrationStep(val title: String, val subtitle: String) {
    SOBRE_VOCE(
        title = "Sobre você",
        subtitle = "Vamos começar nos conhecendo. Suas informações ficam protegidas.",
    ),
    ENDERECO(
        title = "Onde você está",
        subtitle = "O endereço define o BLH mais próximo e a área de coleta domiciliar.",
    ),
    SAUDE(
        title = "Saúde e triagem",
        subtitle = "Uma triagem rápida: a equipe do banco confirma tudo no primeiro contato.",
    ),
    REVISAO(
        title = "Revise seus dados",
        subtitle = "Confira os dados antes de enviar. Você pode voltar e ajustar.",
    ),
}

object RegistrationField {
    const val FULL_NAME = "fullName"
    const val EMAIL = "email"
    const val PHONE = "phone"
    const val BIRTH_DATE = "birthDate"
    const val ZIP_CODE = "zipCode"
    const val STREET = "street"
    const val NUMBER = "number"
    const val NEIGHBORHOOD = "neighborhood"
    const val CITY = "city"
    const val STATE = "state"
    const val BABY_AGE = "babyAgeMonths"
    const val MEDICATION = "medicationNotes"
}

class RegistrationViewModel : ViewModel() {

    var step by mutableStateOf(RegistrationStep.SOBRE_VOCE)
        private set
    var draft by mutableStateOf(RegistrationDraft())
        private set
    var isSubmitting by mutableStateOf(false)
        private set

    val errors = mutableStateMapOf<String, String>()

    val stepNumber: Int get() = step.ordinal + 1
    val totalSteps: Int get() = RegistrationStep.entries.size
    val isLastStep: Boolean get() = step == RegistrationStep.REVISAO
    val isFirstStep: Boolean get() = step == RegistrationStep.SOBRE_VOCE

    fun updateDraft(transform: (RegistrationDraft) -> RegistrationDraft) {
        draft = transform(draft)
    }

    fun updateField(field: String, transform: (RegistrationDraft) -> RegistrationDraft) {
        draft = transform(draft)
        errors.remove(field)
    }

    fun goToPreviousStep() {
        if (isFirstStep) return
        errors.clear()
        step = RegistrationStep.entries[step.ordinal - 1]
    }

    fun validateAndAdvance(): Boolean {
        if (!validateCurrentStep()) return false
        if (!isLastStep) {
            step = RegistrationStep.entries[step.ordinal + 1]
            return false
        }
        return true
    }

    private fun validateCurrentStep(): Boolean {
        errors.clear()
        when (step) {
            RegistrationStep.SOBRE_VOCE -> {
                if (draft.fullName.trim().length < 3) {
                    errors[RegistrationField.FULL_NAME] = "Informe o seu nome completo."
                }
                val email = draft.email.trim()
                if (email.isEmpty()) {
                    errors[RegistrationField.EMAIL] = "Informe o seu e-mail."
                } else if (!email.contains("@") || !email.contains(".")) {
                    errors[RegistrationField.EMAIL] = "Informe um e-mail válido."
                }
                val phone = draft.phone.trim()
                if (phone.length < 11 || phone.length > 15) {
                    errors[RegistrationField.PHONE] = "Informe um telefone para contato."
                }
                if (!isValidBirthDate(draft.birthDate.trim())) {
                    errors[RegistrationField.BIRTH_DATE] = "Informe a sua data de nascimento."
                }
            }

            RegistrationStep.ENDERECO -> {
                if (draft.zipCode.trim().filter { it.isDigit() }.length != 8) {
                    errors[RegistrationField.ZIP_CODE] = "Informe um CEP válido."
                }
                if (draft.street.trim().isEmpty()) {
                    errors[RegistrationField.STREET] = "Informe a rua."
                }
                if (draft.number.trim().isEmpty()) {
                    errors[RegistrationField.NUMBER] = "Obrigatório."
                }
                if (draft.neighborhood.trim().isEmpty()) {
                    errors[RegistrationField.NEIGHBORHOOD] = "Obrigatório."
                }
                if (draft.city.trim().isEmpty()) {
                    errors[RegistrationField.CITY] = "Obrigatório."
                }
                if (draft.state.trim().length != 2) {
                    errors[RegistrationField.STATE] = "UF"
                }
            }

            RegistrationStep.SAUDE -> {
                if (draft.babyAgeMonths.trim().isEmpty()) {
                    errors[RegistrationField.BABY_AGE] = "Informe a idade do seu bebê."
                }
                if (draft.takesMedication && draft.medicationNotes.trim().isEmpty()) {
                    errors[RegistrationField.MEDICATION] =
                        "Descreva os medicamentos para a triagem."
                }
            }

            RegistrationStep.REVISAO -> Unit
        }
        return errors.isEmpty()
    }

    fun submit(onCompleted: () -> Unit) {
        viewModelScope.launch {
            isSubmitting = true
            try {
                draft = draft.copy(state = draft.state.trim().uppercase())
                ServiceLocator.registerDonor(draft)
                onCompleted()
            } finally {
                isSubmitting = false
            }
        }
    }

    private fun isValidBirthDate(value: String): Boolean = try {
        BIRTH_DATE_FORMAT.parse(value)
        true
    } catch (_: Exception) {
        false
    }

    private companion object {
        val BIRTH_DATE_FORMAT: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT)
    }
}
