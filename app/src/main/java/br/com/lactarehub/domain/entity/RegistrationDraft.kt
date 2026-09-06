package br.com.lactarehub.domain.entity

data class RegistrationDraft(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val birthDate: String = "",

    val zipCode: String = "",
    val street: String = "",
    val number: String = "",
    val neighborhood: String = "",
    val city: String = "",
    val state: String = "",

    val babyAgeMonths: String = "",
    val isBreastfeeding: Boolean = true,
    val takesMedication: Boolean = false,
    val medicationNotes: String = "",

    val acceptedTerms: Boolean = false,
) {
    val formattedAddress: String get() = "$street, $number — $neighborhood, $city/$state"
}
