package br.com.lactarehub.domain.entity

enum class TestimonialType(val label: String, val filterLabel: String) {
    PRIMEIRA_DOACAO(label = "1ª doação", filterLabel = "Primeira doação"),
    RECORRENTE(label = "Recorrente", filterLabel = "Recorrentes"),
}

data class Testimonial(
    val id: String,
    val authorName: String,
    val city: String,
    val state: String,
    val message: String,
    val type: TestimonialType,
    val avatarGradientIndex: Int,
) {
    val cityAndState: String get() = "$city, $state"
}
