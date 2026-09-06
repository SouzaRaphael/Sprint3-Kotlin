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

    fun toggleSaved(): Boolean {
        isSaved = !isSaved
        return isSaved
    }
}

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
