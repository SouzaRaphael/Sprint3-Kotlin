package br.com.lactarehub.domain.entity

/** Símbolos dos três passos apresentados na landing. */
enum class HowItWorksIcon { PESSOA, LOCAL, CORACAO }

/** Um dos passos da seção "Em 3 passos você se torna parte da rede". */
data class HowItWorksStep(
    /** Numeral exibido em destaque: `01`, `02`, `03`. */
    val number: String,
    val title: String,
    val description: String,
    val icon: HowItWorksIcon,
)

/** Números agregados da rede, exibidos na landing pública. */
data class ImpactStats(
    val litersCollected: Int,
    val collectionYear: Int,
    val babiesAssisted: Int,
    val donorsInNetwork: Int,
    val connectedBanks: Int,
    val states: Int,
    /** Siglas exibidas nos avatares empilhados do hero. */
    val highlightedStates: List<String>,
)
