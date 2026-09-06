package br.com.lactarehub.domain.entity

data class Donor(
    val id: String,
    val fullName: String,
    val firstName: String,

    val email: String,
    val phone: String,
    val birthDate: String,

    val zipCode: String,
    val street: String,
    val number: String,
    val neighborhood: String,
    val city: String,
    val state: String,

    val babyAgeMonths: String,
    val isBreastfeeding: Boolean,
    val takesMedication: Boolean,
    val medicationNotes: String,

    val completedDonations: Int,
    val donatedMilliliters: Int,
    val babiesReached: Int,
    val streakWeeks: Int,
    val donationsToNextBadge: Int,
    val daysSinceLastDonation: Int?,
    val avatarGradientIndex: Int,
) {
    val isStartingJourney: Boolean get() = completedDonations == 0

    val cityAndState: String get() = "$city, $state"

    val formattedAddress: String get() = "$street, $number — $neighborhood, $city/$state"

    companion object {
        fun fromRegistration(draft: RegistrationDraft): Donor {
            val name = draft.fullName.trim()

            return Donor(
                id = "doadora-${draft.email.trim().lowercase()}",
                fullName = name,
                firstName = firstNameOf(name),
                email = draft.email,
                phone = draft.phone,
                birthDate = draft.birthDate,
                zipCode = draft.zipCode,
                street = draft.street,
                number = draft.number,
                neighborhood = draft.neighborhood,
                city = draft.city,
                state = draft.state,
                babyAgeMonths = draft.babyAgeMonths,
                isBreastfeeding = draft.isBreastfeeding,
                takesMedication = draft.takesMedication,
                medicationNotes = draft.medicationNotes,
                completedDonations = 0,
                donatedMilliliters = 0,
                babiesReached = 0,
                streakWeeks = 0,
                donationsToNextBadge = 1,
                daysSinceLastDonation = null,
                avatarGradientIndex = gradientIndexFor(name),
            )
        }

        private fun firstNameOf(fullName: String): String {
            val parts = fullName.split(Regex("\\s+"))
            return if (parts.isEmpty() || parts.first().isEmpty()) fullName else parts.first()
        }

        private fun gradientIndexFor(fullName: String): Int =
            fullName.sumOf { it.code }
    }
}
