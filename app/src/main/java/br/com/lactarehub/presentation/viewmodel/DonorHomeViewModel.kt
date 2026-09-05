package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.Donor
import kotlinx.coroutines.launch

/** Estado da home da doadora. */
class DonorHomeViewModel : ViewModel() {

    var donor by mutableStateOf<Donor?>(null)
        private set

    /** Nula quando não há coleta marcada. */
    var schedule by mutableStateOf<CollectionSchedule?>(null)
        private set

    /** Nula enquanto a pessoa não tiver nenhuma doação em trânsito. */
    var currentDonation by mutableStateOf<Donation?>(null)
        private set

    var featuredArticles by mutableStateOf<List<Article>>(emptyList())
        private set
    var isLoading by mutableStateOf(true)
        private set
    var isConfirming by mutableStateOf(false)
        private set

    init {
        load()
    }

    /** Primeira carga: mostra o indicador enquanto busca. */
    fun load() {
        viewModelScope.launch {
            isLoading = true
            fetch()
            isLoading = false
        }
    }

    /**
     * Revalidação silenciosa, usada quando a aba volta a ficar visível.
     *
     * Mantém o conteúdo atual na tela em vez de piscar um indicador a cada
     * troca de aba — e é o que faz uma coleta agendada em outra aba aparecer
     * aqui imediatamente.
     */
    fun refresh() {
        viewModelScope.launch { fetch() }
    }

    private suspend fun fetch() {
        donor = ServiceLocator.getDonorProfile()
        schedule = ServiceLocator.getNextCollection()
        currentDonation = ServiceLocator.getCurrentDonation()
        featuredArticles = ServiceLocator.listFeaturedArticles()
    }

    /** Confirma a coleta agendada e devolve o estado atualizado. */
    fun confirmCollection(onConfirmed: () -> Unit) {
        if (schedule == null) return

        viewModelScope.launch {
            isConfirming = true
            schedule = ServiceLocator.confirmCollection()
            isConfirming = false
            onConfirmed()
        }
    }
}
