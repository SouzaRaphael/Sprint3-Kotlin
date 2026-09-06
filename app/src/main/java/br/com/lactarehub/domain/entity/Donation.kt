package br.com.lactarehub.domain.entity

import java.time.LocalDate

enum class DonationStatus(val label: String) {
    EM_ANALISE("Em análise"),
    EM_ANDAMENTO("Em andamento"),
    APROVADA("Aprovada"),
    DISTRIBUIDA("Distribuída"),
}

data class Donation(
    val code: String,
    val collectedAt: LocalDate,
    val volumeMilliliters: Int,
    val status: DonationStatus,
    val collectionPlace: String,
    val destinationHospital: String,
    val timeline: List<TrackingStep>,
)
