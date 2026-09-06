package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.Achievement
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import kotlinx.coroutines.launch

class MyAreaViewModel : ViewModel() {

    var donor by mutableStateOf<Donor?>(null)
        private set

    var schedule by mutableStateOf<CollectionSchedule?>(null)
        private set

    var currentDonation by mutableStateOf<Donation?>(null)
        private set

    var achievements by mutableStateOf<List<Achievement>>(emptyList())
        private set
    var readings by mutableStateOf<List<Article>>(emptyList())
        private set
    var isLoading by mutableStateOf(true)
        private set

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            isLoading = true
            fetch()
            isLoading = false
        }
    }

    fun refresh() {
        viewModelScope.launch { fetch() }
    }

    private suspend fun fetch() {
        donor = ServiceLocator.getDonorProfile()
        schedule = ServiceLocator.getNextCollection()
        currentDonation = ServiceLocator.getCurrentDonation()
        achievements = ServiceLocator.getAchievements()
        readings = ServiceLocator.listFeaturedArticles()
    }
}
