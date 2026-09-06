package br.com.lactarehub.domain.entity

import br.com.lactarehub.core.util.Formatters

enum class CollectionPointType(val label: String) {
    BLH("BLH"),
    POSTO_DE_COLETA("Posto de coleta"),
    COLETA_DOMICILIAR("Coleta domiciliar"),
}

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
    val mapX: Float,
    val mapY: Float,
) {
    val summary: String
        get() = "${type.label} · ${Formatters.oneDecimal(distanceKm)} km · $openingHours"
}
