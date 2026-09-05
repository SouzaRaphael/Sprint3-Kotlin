package br.com.lactarehub.core.di

import br.com.lactarehub.data.repository.ArticleRepositoryImpl
import br.com.lactarehub.data.repository.AuthRepositoryImpl
import br.com.lactarehub.data.repository.CollectionPointRepositoryImpl
import br.com.lactarehub.data.repository.DonationRepositoryImpl
import br.com.lactarehub.data.repository.DonorRepositoryImpl
import br.com.lactarehub.data.repository.InstitutionalRepositoryImpl
import br.com.lactarehub.data.repository.ScheduleRepositoryImpl
import br.com.lactarehub.data.repository.TestimonialRepositoryImpl
import br.com.lactarehub.domain.usecase.ConfirmCollection
import br.com.lactarehub.domain.usecase.GetAchievements
import br.com.lactarehub.domain.usecase.GetArticle
import br.com.lactarehub.domain.usecase.GetAvailableWindows
import br.com.lactarehub.domain.usecase.GetCollectionPoint
import br.com.lactarehub.domain.usecase.GetCurrentDonation
import br.com.lactarehub.domain.usecase.GetDonation
import br.com.lactarehub.domain.usecase.GetDonorProfile
import br.com.lactarehub.domain.usecase.GetImpactStats
import br.com.lactarehub.domain.usecase.GetNextCollection
import br.com.lactarehub.domain.usecase.GetReferenceDate
import br.com.lactarehub.domain.usecase.GetTestCredentials
import br.com.lactarehub.domain.usecase.ListArticles
import br.com.lactarehub.domain.usecase.ListCollectionPoints
import br.com.lactarehub.domain.usecase.ListDonations
import br.com.lactarehub.domain.usecase.ListFeaturedArticles
import br.com.lactarehub.domain.usecase.ListHowItWorksSteps
import br.com.lactarehub.domain.usecase.ListTestimonials
import br.com.lactarehub.domain.usecase.RegisterDonor
import br.com.lactarehub.domain.usecase.ScheduleCollection
import br.com.lactarehub.domain.usecase.SignIn
import br.com.lactarehub.domain.usecase.SignOut
import br.com.lactarehub.domain.usecase.SubmitTestimonial

/**
 * Único ponto do aplicativo em que a camada de apresentação encosta nas
 * implementações concretas de `data`.
 *
 * As telas recebem apenas casos de uso, o que mantém a dependência apontando
 * sempre para o domínio. Registro manual — sem biblioteca de injeção.
 */
object ServiceLocator {

    // ── Repositórios ─────────────────────────────────────────────
    private val authRepository = AuthRepositoryImpl()
    private val donorRepository = DonorRepositoryImpl()
    private val donationRepository = DonationRepositoryImpl()
    private val scheduleRepository = ScheduleRepositoryImpl()
    private val pointRepository = CollectionPointRepositoryImpl()
    private val articleRepository = ArticleRepositoryImpl()
    private val testimonialRepository = TestimonialRepositoryImpl()
    private val institutionalRepository = InstitutionalRepositoryImpl()

    // ── Autenticação ─────────────────────────────────────────────
    val signIn = SignIn(authRepository)
    val registerDonor = RegisterDonor(authRepository)
    val signOut = SignOut(authRepository)
    val getTestCredentials = GetTestCredentials(authRepository)

    // ── Doadora ──────────────────────────────────────────────────
    val getDonorProfile = GetDonorProfile(donorRepository)
    val getAchievements = GetAchievements(donorRepository)

    // ── Doações ──────────────────────────────────────────────────
    val listDonations = ListDonations(donationRepository)
    val getCurrentDonation = GetCurrentDonation(donationRepository)
    val getDonation = GetDonation(donationRepository)

    // ── Agenda ───────────────────────────────────────────────────
    val getNextCollection = GetNextCollection(scheduleRepository)
    val confirmCollection = ConfirmCollection(scheduleRepository)
    val scheduleCollection = ScheduleCollection(scheduleRepository)
    val getAvailableWindows = GetAvailableWindows(scheduleRepository)
    val getReferenceDate = GetReferenceDate(scheduleRepository)

    // ── Pontos de coleta ─────────────────────────────────────────
    val listCollectionPoints = ListCollectionPoints(pointRepository)
    val getCollectionPoint = GetCollectionPoint(pointRepository)

    // ── Conteúdo ─────────────────────────────────────────────────
    val listArticles = ListArticles(articleRepository)
    val listFeaturedArticles = ListFeaturedArticles(articleRepository)
    val getArticle = GetArticle(articleRepository)

    // ── Depoimentos ──────────────────────────────────────────────
    val listTestimonials = ListTestimonials(testimonialRepository)
    val submitTestimonial = SubmitTestimonial(testimonialRepository)

    // ── Institucional ────────────────────────────────────────────
    val getImpactStats = GetImpactStats(institutionalRepository)
    val listHowItWorksSteps = ListHowItWorksSteps(institutionalRepository)
}
