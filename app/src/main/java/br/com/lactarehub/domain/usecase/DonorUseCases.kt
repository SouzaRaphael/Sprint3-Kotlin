package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.repository.DonorRepository

/** Recupera o perfil da doadora autenticada. */
class GetDonorProfile(private val repository: DonorRepository) {
    suspend operator fun invoke(): Donor = repository.getProfile()
}

/** Lista as medalhas da trilha da doadora. */
class GetAchievements(private val repository: DonorRepository) {
    suspend operator fun invoke(): List<Achievement> = repository.getAchievements()
}
