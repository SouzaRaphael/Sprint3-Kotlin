package br.com.lactarehub.domain.entity

import androidx.compose.ui.graphics.Color

/** Trilhas de conteúdo educativo do aplicativo. */
enum class ArticleCategory(val label: String) {
    BENEFICIOS("Benefícios"),
    COMO_ARMAZENAR("Como armazenar"),
    AMAMENTACAO("Amamentação"),
    CUIDADOS("Cuidados"),
    BASTIDORES("Bastidores"),
}

/** Artigo educativo apresentado na aba Conteúdo. */
data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val category: ArticleCategory,
    val readingMinutes: Int,
    /** Cor sólida da capa — o design usa blocos de cor, não fotos. */
    val coverColor: Color,
    /** Corpo do artigo, um parágrafo por item. */
    val paragraphs: List<String>,
    val author: String,
) {
    val readingLabel: String get() = "$readingMinutes min de leitura"
}
