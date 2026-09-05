package br.com.lactarehub.domain.entity

/**
 * Dados coletados ao longo das quatro etapas do cadastro.
 *
 * É imutável: cada etapa devolve uma cópia com os campos preenchidos,
 * o que mantém o controlador do formulário previsível.
 */
data class RegistrationDraft(
    // Etapa 1 — Sobre você
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val birthDate: String = "",

    // Etapa 2 — Onde você está
    val zipCode: String = "",
    val street: String = "",
    val number: String = "",
    val neighborhood: String = "",
    val city: String = "",
    val state: String = "",

    // Etapa 3 — Saúde e triagem
    val babyAgeMonths: String = "",
    val isBreastfeeding: Boolean = true,
    val takesMedication: Boolean = false,
    val medicationNotes: String = "",

    // Etapa 4 — Revisão
    val acceptedTerms: Boolean = false,
) {
    val formattedAddress: String get() = "$street, $number — $neighborhood, $city/$state"
}
