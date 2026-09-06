package br.com.lactarehub.data.repository

import br.com.lactarehub.data.datasource.ArticleMockDatasource
import br.com.lactarehub.data.datasource.CollectionPointMockDatasource
import br.com.lactarehub.data.datasource.InstitutionalMockDatasource
import br.com.lactarehub.data.datasource.ScheduleMockDatasource
import br.com.lactarehub.data.datasource.SessionMockDatasource
import br.com.lactarehub.data.datasource.TestimonialMockDatasource
import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.DonationStatus
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.entity.HowItWorksStep
import br.com.lactarehub.domain.entity.ImpactStats
import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.domain.repository.ArticleRepository
import br.com.lactarehub.domain.repository.CollectionPointRepository
import br.com.lactarehub.domain.repository.DonationRepository
import br.com.lactarehub.domain.repository.DonorRepository
import br.com.lactarehub.domain.repository.InstitutionalRepository
import br.com.lactarehub.domain.repository.ScheduleRepository
import br.com.lactarehub.domain.repository.TestimonialRepository
import kotlinx.coroutines.delay
import java.time.LocalDate

private const val LATENCY_MS = 250L

class ArticleRepositoryImpl : ArticleRepository {

    override suspend fun listArticles(): List<Article> {
        delay(LATENCY_MS)
        return ArticleMockDatasource.items
    }

    override suspend fun listFeatured(): List<Article> {
        delay(LATENCY_MS)
        return ArticleMockDatasource.items.filter { it.id in ArticleMockDatasource.featuredIds }
    }

    override suspend fun getById(id: String): Article? {
        delay(LATENCY_MS)
        return ArticleMockDatasource.items.firstOrNull { it.id == id }
    }
}

class CollectionPointRepositoryImpl : CollectionPointRepository {

    override suspend fun listPoints(): List<CollectionPoint> {
        delay(LATENCY_MS)
        return CollectionPointMockDatasource.items
    }

    override suspend fun getById(id: String): CollectionPoint? {
        delay(LATENCY_MS)
        return CollectionPointMockDatasource.items.firstOrNull { it.id == id }
    }
}

class DonorRepositoryImpl : DonorRepository {

    override suspend fun getProfile(): Donor {
        delay(LATENCY_MS)
        return SessionMockDatasource.donor
    }

    override suspend fun getAchievements(): List<Achievement> {
        delay(LATENCY_MS)
        return SessionMockDatasource.achievements
    }
}

class DonationRepositoryImpl : DonationRepository {

    override suspend fun listDonations(): List<Donation> {
        delay(LATENCY_MS)
        return SessionMockDatasource.donations
    }

    override suspend fun getCurrentDonation(): Donation? {
        delay(LATENCY_MS)

        val donations = SessionMockDatasource.donations
        if (donations.isEmpty()) return null

        return donations.firstOrNull { it.status != DonationStatus.DISTRIBUIDA }
            ?: donations.first()
    }

    override suspend fun getByCode(code: String): Donation? {
        delay(LATENCY_MS)
        return SessionMockDatasource.donations.firstOrNull { it.code == code }
    }
}

class ScheduleRepositoryImpl : ScheduleRepository {

    override suspend fun getNextCollection(): CollectionSchedule? {
        delay(LATENCY_MS)
        return SessionMockDatasource.nextCollection
    }

    override suspend fun confirm(): CollectionSchedule? {
        delay(WRITE_LATENCY_MS)

        val current = SessionMockDatasource.nextCollection ?: return null

        SessionMockDatasource.setNextCollection(current.copy(isConfirmed = true))
        return SessionMockDatasource.nextCollection
    }

    override suspend fun create(schedule: CollectionSchedule): CollectionSchedule {
        delay(WRITE_LATENCY_MS)
        SessionMockDatasource.setNextCollection(schedule)
        return schedule
    }

    override fun listAvailableWindows(): List<String> = ScheduleMockDatasource.availableWindows

    override fun referenceToday(): LocalDate = ScheduleMockDatasource.today

    private companion object {
        const val WRITE_LATENCY_MS = 400L
    }
}

class TestimonialRepositoryImpl : TestimonialRepository {

    override suspend fun listTestimonials(): List<Testimonial> {
        delay(LATENCY_MS)
        return TestimonialMockDatasource.items.toList()
    }

    override suspend fun submit(testimonial: Testimonial): List<Testimonial> {
        delay(SUBMIT_LATENCY_MS)
        TestimonialMockDatasource.items.add(0, testimonial)
        return TestimonialMockDatasource.items.toList()
    }

    private companion object {
        const val SUBMIT_LATENCY_MS = 600L
    }
}

class InstitutionalRepositoryImpl : InstitutionalRepository {

    override suspend fun getImpactStats(): ImpactStats {
        delay(LATENCY_MS)
        return InstitutionalMockDatasource.stats
    }

    override suspend fun listHowItWorksSteps(): List<HowItWorksStep> {
        delay(LATENCY_MS)
        return InstitutionalMockDatasource.howItWorks
    }
}
