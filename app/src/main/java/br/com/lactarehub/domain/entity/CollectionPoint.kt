package br.com.lactarehub.domain.entity

import br.com.lactarehub.core.util.Formatters

/** Natureza do ponto exibido no mapa. */
enum class CollectionPointType(val label: String) {
    BLH("BLH"),
    POSTO_DE_COLETA("Posto de coleta"),
    COLETA_DOMICILIAR("Coleta domiciliar"),
}

/** Banco de Leite Humano, posto de coleta ou área de coleta domiciliar. */
data class CollectionPoint(
    val id: String,
    val name: String,
    val type: CollectionPointType,
    val distanceKm: Double,
    val openingHours: String,
    val address: String,
    val neighborhood: String,
    val phone: String,
    val isOpenNow: Boolean,
    /** Posição relativa no mapa ilustrado (0..1 em cada eixo). */
    val mapX: Float,
    val mapY: Float,
) {
    /** Linha de resumo mostrada sob o nome: `BLH · 1.2 km · 08h-18h`. */
    val summary: String
        get() = "${type.label} · ${Formatters.oneDecimal(distanceKm)} km · $openingHours"
}
