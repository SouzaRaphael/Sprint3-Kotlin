package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.repository.DonationRepository

/** Lista o histórico de doações da doadora. */
class ListDonations(private val repository: DonationRepository) {
    suspend operator fun invoke(): List<Donation> = repository.listDonations()
}

/** Doação em trânsito, usada na prévia de rastreamento. */
class GetCurrentDonation(private val repository: DonationRepository) {
    suspend operator fun invoke(): Donation? = repository.getCurrentDonation()
}

/** Doação pelo código de rastreio, para a tela de detalhe. */
class GetDonation(private val repository: DonationRepository) {
    suspend operator fun invoke(code: String): Donation? = repository.getByCode(code)
}
