package br.com.lactarehub.domain.entity

/** Recorte usado nos filtros da tela de depoimentos. */
enum class TestimonialType(val label: String, val filterLabel: String) {
    PRIMEIRA_DOACAO(label = "1ª doação", filterLabel = "Primeira doação"),
    RECORRENTE(label = "Recorrente", filterLabel = "Recorrentes"),
}

/** Depoimento de uma doadora da rede. */
data class Testimonial(
    val id: String,
    val authorName: String,
    val city: String,
    val state: String,
    val message: String,
    val type: TestimonialType,
    /** Índice do gradiente de avatar em `AppColors.avatarGradients`. */
    val avatarGradientIndex: Int,
) {
    val cityAndState: String get() = "$city, $state"
}
