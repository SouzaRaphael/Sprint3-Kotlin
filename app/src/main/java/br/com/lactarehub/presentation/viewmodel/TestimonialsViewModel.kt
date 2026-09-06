package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.domain.entity.TestimonialType
import kotlinx.coroutines.launch

class TestimonialsViewModel : ViewModel() {

    var testimonials by mutableStateOf<List<Testimonial>>(emptyList())
        private set
    var type by mutableStateOf<TestimonialType?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set

    val filterLabels: List<String>
        get() = listOf("Todos") + TestimonialType.entries.map { it.filterLabel }

    val selectedFilterIndex: Int
        get() = type?.let { it.ordinal + 1 } ?: 0

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            isLoading = true
            testimonials = ServiceLocator.listTestimonials(type)
            isLoading = false
        }
    }

    fun selectFilter(index: Int) {
        type = if (index == 0) null else TestimonialType.entries[index - 1]
        load()
    }

    fun refresh() {
        viewModelScope.launch { testimonials = ServiceLocator.listTestimonials(type) }
    }
}
