package br.com.lactarehub.domain.entity

/** Situação de uma etapa na linha do tempo de rastreamento. */
enum class TrackingStepStatus { CONCLUIDA, ATUAL, PENDENTE }

/** Etapa do percurso do leite doado, do coletado ao hospital. */
data class TrackingStep(
    val title: String,
    val description: String,
    val status: TrackingStepStatus,
)
