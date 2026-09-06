package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.AchievementIcon
import br.com.lactarehub.domain.entity.AchievementStatus
import br.com.lactarehub.domain.entity.Donor

object DonorMockDatasource {

    val demoProfile = Donor(
        id = "doadora-giovana",
        fullName = "Giovana Aparecida Ramos",
        firstName = "Giovana",
        email = "giovana@email.com",
        phone = "(11) 98421-7730",
        birthDate = "14/03/1994",
        zipCode = "04101-300",
        street = "Rua Domingos de Morais",
        number = "1284",
        neighborhood = "Vila Mariana",
        city = "São Paulo",
        state = "SP",
        babyAgeMonths = "5",
        isBreastfeeding = true,
        takesMedication = false,
        medicationNotes = "",
        completedDonations = 14,
        donatedMilliliters = 3200,
        babiesReached = 9,
        streakWeeks = 7,
        donationsToNextBadge = 1,
        daysSinceLastDonation = 3,
        avatarGradientIndex = 0,
    )

    val demoAchievements: List<Achievement> = listOf(
        Achievement(
            id = "conq-primeira-doacao",
            title = "Primeira doação",
            progressLabel = "Conquistada",
            status = AchievementStatus.CONQUISTADA,
            gradientIndex = 3,
            icon = AchievementIcon.GOTA,
        ),
        Achievement(
            id = "conq-doadora-frequente",
            title = "Doadora frequente",
            progressLabel = "14 doações",
            status = AchievementStatus.CONQUISTADA,
            gradientIndex = 5,
            icon = AchievementIcon.MEDALHA,
        ),
        Achievement(
            id = "conq-embaixadora",
            title = "Embaixadora",
            progressLabel = "2/5 indicações",
            status = AchievementStatus.CONQUISTADA,
            gradientIndex = 2,
            icon = AchievementIcon.ESTRELA,
        ),
        Achievement(
            id = "conq-50-dias",
            title = "50 dias seguidos",
            progressLabel = "Em progresso",
            status = AchievementStatus.EM_PROGRESSO,
            gradientIndex = 0,
            icon = AchievementIcon.CORACAO,
        ),
        Achievement(
            id = "conq-inverno-solidario",
            title = "Inverno solidário",
            progressLabel = "Bloqueada",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 1,
            icon = AchievementIcon.FOLHA,
        ),
        Achievement(
            id = "conq-top-1",
            title = "Top 1% 2026",
            progressLabel = "Bloqueada",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 4,
            icon = AchievementIcon.BRILHO,
        ),
    )

    val startingAchievements: List<Achievement> = listOf(
        Achievement(
            id = "conq-primeira-doacao",
            title = "Primeira doação",
            progressLabel = "Falta 1 doação",
            status = AchievementStatus.EM_PROGRESSO,
            gradientIndex = 3,
            icon = AchievementIcon.GOTA,
        ),
        Achievement(
            id = "conq-doadora-frequente",
            title = "Doadora frequente",
            progressLabel = "0/10 doações",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 5,
            icon = AchievementIcon.MEDALHA,
        ),
        Achievement(
            id = "conq-embaixadora",
            title = "Embaixadora",
            progressLabel = "0/5 indicações",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 2,
            icon = AchievementIcon.ESTRELA,
        ),
        Achievement(
            id = "conq-50-dias",
            title = "50 dias seguidos",
            progressLabel = "Bloqueada",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 0,
            icon = AchievementIcon.CORACAO,
        ),
        Achievement(
            id = "conq-inverno-solidario",
            title = "Inverno solidário",
            progressLabel = "Bloqueada",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 1,
            icon = AchievementIcon.FOLHA,
        ),
        Achievement(
            id = "conq-top-1",
            title = "Top 1% 2026",
            progressLabel = "Bloqueada",
            status = AchievementStatus.BLOQUEADA,
            gradientIndex = 4,
            icon = AchievementIcon.BRILHO,
        ),
    )
}
