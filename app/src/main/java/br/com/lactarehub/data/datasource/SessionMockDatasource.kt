package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.entity.RegistrationDraft

/**
 * Quem está usando o aplicativo agora.
 *
 * Os demais datasources são catálogo — a persona de demonstração, as doações
 * e a agenda de exemplo. É aqui que se decide o que a sessão atual enxerga,
 * e é o que separa quem entrou pelo login de quem acabou de se cadastrar.
 *
 * O estado vive em memória e se perde ao fechar o aplicativo, como o restante
 * dos mocks desta Sprint.
 */
object SessionMockDatasource {

    private var currentDonor: Donor = DonorMockDatasource.demoProfile
    private var currentSchedule: CollectionSchedule? = ScheduleMockDatasource.demoNext
    private var currentDonations: List<Donation> = DonationMockDatasource.demoItems
    private var currentAchievements: List<Achievement> = DonorMockDatasource.demoAchievements

    val donor: Donor get() = currentDonor
    val donations: List<Donation> get() = currentDonations.toList()
    val achievements: List<Achievement> get() = currentAchievements

    /** Nula quando não há coleta marcada — o caso de quem acabou de entrar. */
    val nextCollection: CollectionSchedule? get() = currentSchedule

    /** Sessão da persona com histórico, aberta pelas credenciais de teste. */
    fun startDemoSession() {
        currentDonor = DonorMockDatasource.demoProfile
        currentSchedule = ScheduleMockDatasource.demoNext
        currentDonations = DonationMockDatasource.demoItems
        currentAchievements = DonorMockDatasource.demoAchievements
    }

    /**
     * Sessão de quem concluiu o cadastro: identidade própria e jornada em
     * branco, sem coleta agendada nem doações a rastrear.
     */
    fun startRegisteredSession(draft: RegistrationDraft) {
        currentDonor = Donor.fromRegistration(draft)
        currentSchedule = null
        currentDonations = emptyList()
        currentAchievements = DonorMockDatasource.startingAchievements
    }

    /** Marca ou remarca a coleta da sessão. */
    fun setNextCollection(schedule: CollectionSchedule) {
        currentSchedule = schedule
    }
}
