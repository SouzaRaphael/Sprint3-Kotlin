package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.repository.DonorRepository

class GetDonorProfile(private val repository: DonorRepository) {
    suspend operator fun invoke(): Donor = repository.getProfile()
}

class GetAchievements(private val repository: DonorRepository) {
    suspend operator fun invoke(): List<Achievement> = repository.getAchievements()
}
