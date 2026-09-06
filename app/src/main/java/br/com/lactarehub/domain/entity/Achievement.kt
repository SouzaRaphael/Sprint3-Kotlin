package br.com.lactarehub.domain.entity

enum class AchievementStatus(val label: String) {
    CONQUISTADA("Conquistada"),
    EM_PROGRESSO("Em progresso"),
    BLOQUEADA("Bloqueada"),
}

enum class AchievementIcon { GOTA, MEDALHA, ESTRELA, CORACAO, FOLHA, BRILHO }

data class Achievement(
    val id: String,
    val title: String,
    val progressLabel: String,
    val status: AchievementStatus,
    val gradientIndex: Int,
    val icon: AchievementIcon,
)
