package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.Article
import br.com.lactarehub.domain.entity.ArticleCategory
import br.com.lactarehub.domain.repository.ArticleRepository

/** Lista os artigos, opcionalmente restritos a uma categoria. */
class ListArticles(private val repository: ArticleRepository) {
    suspend operator fun invoke(category: ArticleCategory? = null): List<Article> {
        val articles = repository.listArticles()
        if (category == null) return articles
        return articles.filter { it.category == category }
    }
}

/** Seleção curta de leituras para os carrosséis das telas da doadora. */
class ListFeaturedArticles(private val repository: ArticleRepository) {
    suspend operator fun invoke(): List<Article> = repository.listFeatured()
}

/** Recupera um artigo pelo identificador, para a tela de leitura. */
class GetArticle(private val repository: ArticleRepository) {
    suspend operator fun invoke(id: String): Article? = repository.getById(id)
}
