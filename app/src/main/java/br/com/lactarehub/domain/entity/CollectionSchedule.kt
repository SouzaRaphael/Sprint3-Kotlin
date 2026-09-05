package br.com.lactarehub.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

/** Modalidade escolhida para entregar o leite. */
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

/** Coleta agendada da doadora. */
data class CollectionSchedule(
    val id: String,
    val scheduledAt: LocalDateTime,
    val timeWindow: String,
    val mode: CollectionMode,
    val place: String,
    val isConfirmed: Boolean,
    /**
     * "Hoje" segundo o protótipo — permite calcular "em 4 dias" sem que a
     * interface precise conhecer uma data fixa.
     */
    val referenceToday: LocalDate,
    val notes: String = "",
) {
    /** Linha de contexto do card: `Coleta domiciliar · Vila Mariana`. */
    val summary: String get() = "${mode.label} · $place"
}
