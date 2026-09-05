package br.com.lactarehub.domain.entity

/** Nutriz cadastrada na rede Lactare. */
data class Donor(
    val id: String,
    val fullName: String,
    val firstName: String,

    // ── Contato ──────────────────────────────────────────────────
    val email: String,
    val phone: String,
    val birthDate: String,

    // ── Endereço ─────────────────────────────────────────────────
    val zipCode: String,
    val street: String,
    val number: String,
    val neighborhood: String,
    val city: String,
    val state: String,

    // ── Triagem ──────────────────────────────────────────────────
    val babyAgeMonths: String,
    val isBreastfeeding: Boolean,
    val takesMedication: Boolean,
    val medicationNotes: String,

    // ── Jornada ──────────────────────────────────────────────────
    /** Número de doações já concluídas. */
    val completedDonations: Int,
    /** Volume total doado, em mililitros. */
    val donatedMilliliters: Int,
    /** Estimativa de bebês alcançados pelas doações. */
    val babiesReached: Int,
    /** Semanas consecutivas doando. */
    val streakWeeks: Int,
    /** Doações que faltam para a próxima medalha. */
    val donationsToNextBadge: Int,
    /** Dias desde a última doação. Nulo para quem ainda não doou. */
    val daysSinceLastDonation: Int?,
    /** Índice do gradiente de avatar em `AppColors.avatarGradients`. */
    val avatarGradientIndex: Int,
) {
    /** `true` quando a pessoa ainda não concluiu nenhuma doação. */
    val isStartingJourney: Boolean get() = completedDonations == 0

    val cityAndState: String get() = "$city, $state"

    val formattedAddress: String get() = "$street, $number — $neighborhood, $city/$state"

    companion object {
        /**
         * Materializa a doadora a partir do que foi preenchido no cadastro.
         *
         * Toda a jornada começa zerada: quem acabou de se cadastrar ainda não
         * tem doações, sequência nem medalhas.
         */
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

        /**
         * Cor do avatar derivada do nome, para que a mesma pessoa receba
         * sempre o mesmo gradiente. O componente aplica o módulo pelo tamanho
         * da paleta.
         */
        private fun gradientIndexFor(fullName: String): Int =
            fullName.sumOf { it.code }
    }
}
