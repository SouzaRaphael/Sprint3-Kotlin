package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.repository.DonationRepository

class ListDonations(private val repository: DonationRepository) {
    suspend operator fun invoke(): List<Donation> = repository.listDonations()
}

class GetCurrentDonation(private val repository: DonationRepository) {
    suspend operator fun invoke(): Donation? = repository.getCurrentDonation()
}

class GetDonation(private val repository: DonationRepository) {
    suspend operator fun invoke(code: String): Donation? = repository.getByCode(code)
}
