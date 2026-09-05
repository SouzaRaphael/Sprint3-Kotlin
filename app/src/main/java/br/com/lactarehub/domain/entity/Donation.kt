package br.com.lactarehub.domain.entity

import java.time.LocalDate

/** Situação de uma doação dentro do fluxo do banco de leite. */
enum class DonationStatus(val label: String) {
    EM_ANALISE("Em análise"),
    EM_ANDAMENTO("Em andamento"),
    APROVADA("Aprovada"),
    DISTRIBUIDA("Distribuída"),
}

/** Uma doação de leite humano, do recolhimento à entrega no hospital. */
data class Donation(
    /** Código de rastreio exibido para a doadora, ex.: `LCT-2104`. */
    val code: String,
    val collectedAt: LocalDate,
    val volumeMilliliters: Int,
    val status: DonationStatus,
    val collectionPlace: String,
    val destinationHospital: String,
    val timeline: List<TrackingStep>,
)
