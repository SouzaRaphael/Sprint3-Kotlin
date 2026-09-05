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

/** Autenticação e cadastro de novas doadoras. */
interface AuthRepository {
    /** Lança `AuthFailure` quando as credenciais não conferem. */
    suspend fun signIn(email: String, password: String): UserSession

    suspend fun register(draft: RegistrationDraft): UserSession

    /** Encerra a sessão e devolve o aplicativo ao estado de demonstração. */
    suspend fun signOut()

    /** Contas de demonstração mostradas na tela de login. */
    fun listTestCredentials(): List<TestCredential>
}

/** Perfil e conquistas da doadora autenticada. */
interface DonorRepository {
    suspend fun getProfile(): Donor

    suspend fun getAchievements(): List<Achievement>
}

/** Histórico e rastreamento das doações. */
interface DonationRepository {
    suspend fun listDonations(): List<Donation>

    /**
     * Doação mais recente ainda em trânsito na rede. Nula para quem ainda
     * não doou.
     */
    suspend fun getCurrentDonation(): Donation?

    /** Doação pelo código de rastreio, usada pela tela de detalhe. */
    suspend fun getByCode(code: String): Donation?
}

/** Agendamento de coletas. */
interface ScheduleRepository {
    /** Nula quando não há coleta marcada. */
    suspend fun getNextCollection(): CollectionSchedule?

    suspend fun confirm(): CollectionSchedule?

    suspend fun create(schedule: CollectionSchedule): CollectionSchedule

    /** Janelas de horário oferecidas no formulário de agendamento. */
    fun listAvailableWindows(): List<String>

    /** "Hoje" segundo o protótipo, usado para montar as datas do formulário. */
    fun referenceToday(): LocalDate
}

/** Pontos de coleta exibidos no mapa. */
interface CollectionPointRepository {
    suspend fun listPoints(): List<CollectionPoint>

    suspend fun getById(id: String): CollectionPoint?
}

/** Conteúdo educativo da rede. */
interface ArticleRepository {
    suspend fun listArticles(): List<Article>

    /** Seleção curta usada nos carrosséis de leitura. */
    suspend fun listFeatured(): List<Article>

    suspend fun getById(id: String): Article?
}

/** Depoimentos das doadoras. */
interface TestimonialRepository {
    suspend fun listTestimonials(): List<Testimonial>

    /** Publica um novo depoimento e devolve a lista já atualizada. */
    suspend fun submit(testimonial: Testimonial): List<Testimonial>
}

/** Conteúdo institucional da landing pública. */
interface InstitutionalRepository {
    suspend fun getImpactStats(): ImpactStats

    suspend fun listHowItWorksSteps(): List<HowItWorksStep>
}
