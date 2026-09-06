package br.com.lactarehub.domain.entity

enum class HowItWorksIcon { PESSOA, LOCAL, CORACAO }

data class HowItWorksStep(
    val number: String,
    val title: String,
    val description: String,
    val icon: HowItWorksIcon,
)

data class ImpactStats(
    val litersCollected: Int,
    val collectionYear: Int,
    val babiesAssisted: Int,
    val donorsInNetwork: Int,
    val connectedBanks: Int,
    val states: Int,
    val highlightedStates: List<String>,
)
