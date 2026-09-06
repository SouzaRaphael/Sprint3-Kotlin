package br.com.lactarehub.domain.entity

import androidx.compose.ui.graphics.Color

enum class ArticleCategory(val label: String) {
    BENEFICIOS("Benefícios"),
    COMO_ARMAZENAR("Como armazenar"),
    AMAMENTACAO("Amamentação"),
    CUIDADOS("Cuidados"),
    BASTIDORES("Bastidores"),
}

data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val category: ArticleCategory,
    val readingMinutes: Int,
    val coverColor: Color,
    val paragraphs: List<String>,
    val author: String,
) {
    val readingLabel: String get() = "$readingMinutes min de leitura"
}
