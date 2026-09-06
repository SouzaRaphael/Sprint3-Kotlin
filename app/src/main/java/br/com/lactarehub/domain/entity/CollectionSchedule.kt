package br.com.lactarehub.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

enum class CollectionMode(val label: String, val description: String) {
    DOMICILIAR(
        label = "Coleta domiciliar",
        description = "Uma equipe busca o leite no endereço cadastrado.",
    ),
    POSTO_DE_COLETA(
        label = "Levar a um posto",
        description = "Entregue em um dos postos parceiros da rede.",
    ),
    BANCO(
        label = "Levar ao BLH",
        description = "Entregue diretamente no Banco de Leite Humano.",
    ),
}

data class CollectionSchedule(
    val id: String,
    val scheduledAt: LocalDateTime,
    val timeWindow: String,
    val mode: CollectionMode,
    val place: String,
    val isConfirmed: Boolean,
    val referenceToday: LocalDate,
    val notes: String = "",
) {
    val summary: String get() = "${mode.label} · $place"
}
