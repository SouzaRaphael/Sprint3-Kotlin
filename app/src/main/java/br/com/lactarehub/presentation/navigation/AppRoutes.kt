package br.com.lactarehub.presentation.navigation

/**
 * Nomes das rotas. Os caminhos espelham os do protótipo.
 *
 * As telas de detalhe recebem apenas o identificador da entidade e a buscam
 * no repositório — no Flutter o objeto viajava em `settings.arguments`, o que
 * não sobrevive à recriação de processo do Android.
 */
object AppRoutes {
    const val SPLASH = "splash"
    const val LANDING = "landing"
    const val LOGIN = "login"
    const val REGISTRATION = "cadastro"
    const val REGISTRATION_SUCCESS = "cadastro/sucesso"

    /** Casca autenticada com as cinco abas. */
    const val APP = "app"

    const val TESTIMONIALS = "depoimentos"
    const val WRITE_TESTIMONIAL = "depoimentos/novo"
    const val PROFILE = "perfil"

    const val ARTICLE_DETAIL = "conteudo/artigo/{articleId}"
    const val COLLECTION_POINT_DETAIL = "pontos/detalhe/{pointId}"
    const val DONATION_DETAIL = "doacoes/detalhe/{donationCode}"

    const val ARG_ARTICLE_ID = "articleId"
    const val ARG_POINT_ID = "pointId"
    const val ARG_DONATION_CODE = "donationCode"

    fun articleDetail(articleId: String) = "conteudo/artigo/$articleId"

    fun collectionPointDetail(pointId: String) = "pontos/detalhe/$pointId"

    fun donationDetail(code: String) = "doacoes/detalhe/$code"
}
