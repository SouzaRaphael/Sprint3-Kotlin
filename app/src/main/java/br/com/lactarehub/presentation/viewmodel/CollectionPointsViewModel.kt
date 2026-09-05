package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionPointType
import kotlinx.coroutines.launch

/** Estado do mapa de pontos de coleta. */
class CollectionPointsViewModel : ViewModel() {

    var points by mutableStateOf<List<CollectionPoint>>(emptyList())
        private set
    var selected by mutableStateOf<CollectionPoint?>(null)
        private set
    var typeFilter by mutableStateOf<CollectionPointType?>(null)
        private set
    var onlyOpenNow by mutableStateOf(false)
        private set
    var query by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(true)
        private set

    /** Rótulos da barra de filtros: "Todos" seguido dos tipos. */
    val filterLabels: List<String>
        get() = listOf("Todos") + CollectionPointType.entries.map { it.label }

    val selectedFilterIndex: Int
        get() = typeFilter?.let { it.ordinal + 1 } ?: 0

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            isLoading = true
            refreshPoints()
            isLoading = false
        }
    }

    private suspend fun refreshPoints() {
        points = ServiceLocator.listCollectionPoints(
            type = typeFilter,
            onlyOpenNow = onlyOpenNow,
            query = query,
        )

        // Mantém a seleção só enquanto ela continuar visível no filtro atual.
        if (selected != null && selected !in points) selected = null
        if (selected == null) selected = points.firstOrNull()
    }

    fun selectFilter(index: Int) {
        typeFilter = if (index == 0) null else CollectionPointType.entries[index - 1]
        viewModelScope.launch { refreshPoints() }
    }

    fun toggleOpenNow() {
        onlyOpenNow = !onlyOpenNow
        viewModelScope.launch { refreshPoints() }
    }

    fun search(value: String) {
        query = value
        viewModelScope.launch { refreshPoints() }
    }

    fun select(point: CollectionPoint) {
        selected = point
    }
}
