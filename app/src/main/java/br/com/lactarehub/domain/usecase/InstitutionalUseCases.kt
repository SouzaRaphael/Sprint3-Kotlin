package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.HowItWorksStep
import br.com.lactarehub.domain.entity.ImpactStats
import br.com.lactarehub.domain.repository.InstitutionalRepository

/** Números da rede exibidos na landing. */
class GetImpactStats(private val repository: InstitutionalRepository) {
    suspend operator fun invoke(): ImpactStats = repository.getImpactStats()
}

/** Passos da seção "Em 3 passos" da landing. */
class ListHowItWorksSteps(private val repository: InstitutionalRepository) {
    suspend operator fun invoke(): List<HowItWorksStep> = repository.listHowItWorksSteps()
}
