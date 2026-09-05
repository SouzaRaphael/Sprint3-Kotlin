package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import br.com.lactarehub.domain.entity.HowItWorksStep
import br.com.lactarehub.domain.entity.ImpactStats
import kotlinx.coroutines.launch

/** Estado da home pública. */
class LandingViewModel : ViewModel() {

    var stats by mutableStateOf<ImpactStats?>(null)
        private set
    var steps by mutableStateOf<List<HowItWorksStep>>(emptyList())
        private set
    var isLoading by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            stats = ServiceLocator.getImpactStats()
            steps = ServiceLocator.listHowItWorksSteps()
            isLoading = false
        }
    }
}

/**
 * Leitura de um artigo.
 *
 * O identificador chega pela rota — o equivalente ao `settings.arguments`
 * usado na navegação do projeto Flutter, agora com o artigo recuperado do
 * repositório em vez de trafegar pela pilha de navegação.
 */
class ArticleDetailViewModel : ViewModel() {

    var article by mutableStateOf<Article?>(null)
        private set
    var isSaved by mutableStateOf(false)
        private set

    private var loadedId: String? = null

    fun load(id: String) {
        if (loadedId == id) return
        loadedId = id
        viewModelScope.launch { article = ServiceLocator.getArticle(id) }
    }

    /** Alterna o marcador de leitura e devolve o novo estado. */
    fun toggleSaved(): Boolean {
        isSaved = !isSaved
        return isSaved
    }
}

/** Detalhe de um ponto da rede. */
class CollectionPointDetailViewModel : ViewModel() {

    var point by mutableStateOf<CollectionPoint?>(null)
        private set

    private var loadedId: String? = null

    fun load(id: String) {
        if (loadedId == id) return
        loadedId = id
        viewModelScope.launch { point = ServiceLocator.getCollectionPoint(id) }
    }
}

/** Rastreamento completo de uma doação. */
class DonationDetailViewModel : ViewModel() {

    var donation by mutableStateOf<Donation?>(null)
        private set

    private var loadedCode: String? = null

    fun load(code: String) {
        if (loadedCode == code) return
        loadedCode = code
        viewModelScope.launch { donation = ServiceLocator.getDonation(code) }
    }
}

/**
 * Perfil da doadora: dados do cadastro e a coleta agendada no momento.
 *
 * Carrega tudo ao abrir, então sempre reflete o último agendamento —
 * inclusive um acabado de registrar ou alterar.
 */
class ProfileViewModel : ViewModel() {

    var donor by mutableStateOf<Donor?>(null)
        private set
    var schedule by mutableStateOf<CollectionSchedule?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set
    var isSigningOut by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            donor = ServiceLocator.getDonorProfile()
            schedule = ServiceLocator.getNextCollection()
            isLoading = false
        }
    }

    fun signOut(onSignedOut: () -> Unit) {
        viewModelScope.launch {
            isSigningOut = true
            ServiceLocator.signOut()
            onSignedOut()
        }
    }
}
