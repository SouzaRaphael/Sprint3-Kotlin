package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.ArticleCategory
import kotlinx.coroutines.launch

class ContentViewModel : ViewModel() {

    var articles by mutableStateOf<List<Article>>(emptyList())
        private set
    var category by mutableStateOf<ArticleCategory?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set

    val filterLabels: List<String>
        get() = listOf("Todos") + ArticleCategory.entries.map { it.label }

    val selectedFilterIndex: Int
        get() = category?.let { it.ordinal + 1 } ?: 0

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            isLoading = true
            articles = ServiceLocator.listArticles(category)
            isLoading = false
        }
    }

    fun selectFilter(index: Int) {
        category = if (index == 0) null else ArticleCategory.entries[index - 1]
        load()
    }
}
