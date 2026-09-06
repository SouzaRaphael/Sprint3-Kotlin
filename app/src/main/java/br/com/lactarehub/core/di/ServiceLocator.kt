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

object ServiceLocator {

    private val authRepository = AuthRepositoryImpl()
    private val donorRepository = DonorRepositoryImpl()
    private val donationRepository = DonationRepositoryImpl()
    private val scheduleRepository = ScheduleRepositoryImpl()
    private val pointRepository = CollectionPointRepositoryImpl()
    private val articleRepository = ArticleRepositoryImpl()
    private val testimonialRepository = TestimonialRepositoryImpl()
    private val institutionalRepository = InstitutionalRepositoryImpl()

    val signIn = SignIn(authRepository)
    val registerDonor = RegisterDonor(authRepository)
    val signOut = SignOut(authRepository)
    val getTestCredentials = GetTestCredentials(authRepository)

    val getDonorProfile = GetDonorProfile(donorRepository)
    val getAchievements = GetAchievements(donorRepository)

    val listDonations = ListDonations(donationRepository)
    val getCurrentDonation = GetCurrentDonation(donationRepository)
    val getDonation = GetDonation(donationRepository)

    val getNextCollection = GetNextCollection(scheduleRepository)
    val confirmCollection = ConfirmCollection(scheduleRepository)
    val scheduleCollection = ScheduleCollection(scheduleRepository)
    val getAvailableWindows = GetAvailableWindows(scheduleRepository)
    val getReferenceDate = GetReferenceDate(scheduleRepository)

    val listCollectionPoints = ListCollectionPoints(pointRepository)
    val getCollectionPoint = GetCollectionPoint(pointRepository)

    val listArticles = ListArticles(articleRepository)
    val listFeaturedArticles = ListFeaturedArticles(articleRepository)
    val getArticle = GetArticle(articleRepository)

    val listTestimonials = ListTestimonials(testimonialRepository)
    val submitTestimonial = SubmitTestimonial(testimonialRepository)

    val getImpactStats = GetImpactStats(institutionalRepository)
    val listHowItWorksSteps = ListHowItWorksSteps(institutionalRepository)
}
