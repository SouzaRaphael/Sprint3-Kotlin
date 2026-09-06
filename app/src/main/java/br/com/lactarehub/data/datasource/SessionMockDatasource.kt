package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.entity.RegistrationDraft

object SessionMockDatasource {

    private var currentDonor: Donor = DonorMockDatasource.demoProfile
    private var currentSchedule: CollectionSchedule? = ScheduleMockDatasource.demoNext
    private var currentDonations: List<Donation> = DonationMockDatasource.demoItems
    private var currentAchievements: List<Achievement> = DonorMockDatasource.demoAchievements

    val donor: Donor get() = currentDonor
    val donations: List<Donation> get() = currentDonations.toList()
    val achievements: List<Achievement> get() = currentAchievements

    val nextCollection: CollectionSchedule? get() = currentSchedule

    fun startDemoSession() {
        currentDonor = DonorMockDatasource.demoProfile
        currentSchedule = ScheduleMockDatasource.demoNext
        currentDonations = DonationMockDatasource.demoItems
        currentAchievements = DonorMockDatasource.demoAchievements
    }

    fun startRegisteredSession(draft: RegistrationDraft) {
        currentDonor = Donor.fromRegistration(draft)
        currentSchedule = null
        currentDonations = emptyList()
        currentAchievements = DonorMockDatasource.startingAchievements
    }

    fun setNextCollection(schedule: CollectionSchedule) {
        currentSchedule = schedule
    }
}
