package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.ArticleCategory
import br.com.lactarehub.domain.repository.ArticleRepository

class ListArticles(private val repository: ArticleRepository) {
    suspend operator fun invoke(category: ArticleCategory? = null): List<Article> {
        val articles = repository.listArticles()
        if (category == null) return articles
        return articles.filter { it.category == category }
    }
}

class ListFeaturedArticles(private val repository: ArticleRepository) {
    suspend operator fun invoke(): List<Article> = repository.listFeatured()
}

class GetArticle(private val repository: ArticleRepository) {
    suspend operator fun invoke(id: String): Article? = repository.getById(id)
}
