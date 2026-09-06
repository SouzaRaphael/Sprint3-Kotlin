package br.com.lactarehub.domain.repository

import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.entity.HowItWorksStep
import br.com.lactarehub.domain.entity.ImpactStats
import br.com.lactarehub.domain.entity.RegistrationDraft
import br.com.lactarehub.domain.entity.TestCredential
import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.domain.entity.UserSession
import java.time.LocalDate

interface AuthRepository {
    suspend fun signIn(email: String, password: String): UserSession

    suspend fun register(draft: RegistrationDraft): UserSession

    suspend fun signOut()

    fun listTestCredentials(): List<TestCredential>
}

interface DonorRepository {
    suspend fun getProfile(): Donor

    suspend fun getAchievements(): List<Achievement>
}

interface DonationRepository {
    suspend fun listDonations(): List<Donation>

    suspend fun getCurrentDonation(): Donation?

    suspend fun getByCode(code: String): Donation?
}

interface ScheduleRepository {
    suspend fun getNextCollection(): CollectionSchedule?

    suspend fun confirm(): CollectionSchedule?

    suspend fun create(schedule: CollectionSchedule): CollectionSchedule

    fun listAvailableWindows(): List<String>

    fun referenceToday(): LocalDate
}

interface CollectionPointRepository {
    suspend fun listPoints(): List<CollectionPoint>

    suspend fun getById(id: String): CollectionPoint?
}

interface ArticleRepository {
    suspend fun listArticles(): List<Article>

    suspend fun listFeatured(): List<Article>

    suspend fun getById(id: String): Article?
}

interface TestimonialRepository {
    suspend fun listTestimonials(): List<Testimonial>

    suspend fun submit(testimonial: Testimonial): List<Testimonial>
}

interface InstitutionalRepository {
    suspend fun getImpactStats(): ImpactStats

    suspend fun listHowItWorksSteps(): List<HowItWorksStep>
}
