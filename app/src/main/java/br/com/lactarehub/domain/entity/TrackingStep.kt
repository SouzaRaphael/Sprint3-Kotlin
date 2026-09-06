package br.com.lactarehub.domain.entity

enum class TrackingStepStatus { CONCLUIDA, ATUAL, PENDENTE }

data class TrackingStep(
    val title: String,
    val description: String,
    val status: TrackingStepStatus,
)
