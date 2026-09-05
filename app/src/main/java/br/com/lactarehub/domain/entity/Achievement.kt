package br.com.lactarehub.domain.entity

/** Estágio de uma conquista na trilha da doadora. */
enum class AchievementStatus(val label: String) {
    CONQUISTADA("Conquistada"),
    EM_PROGRESSO("Em progresso"),
    BLOQUEADA("Bloqueada"),
}

/**
 * Símbolos usados nas medalhas, mantidos no domínio para que a camada
 * de dados não precise conhecer o pacote de ícones.
 */
enum class AchievementIcon { GOTA, MEDALHA, ESTRELA, CORACAO, FOLHA, BRILHO }

/** Medalha exibida no grid da área da doadora. */
data class Achievement(
    val id: String,
    val title: String,
    /** Texto de progresso: `14 doações`, `2/5 indicações`. */
    val progressLabel: String,
    val status: AchievementStatus,
    /** Índice do gradiente em `AppColors.avatarGradients`. */
    val gradientIndex: Int,
    val icon: AchievementIcon,
)
