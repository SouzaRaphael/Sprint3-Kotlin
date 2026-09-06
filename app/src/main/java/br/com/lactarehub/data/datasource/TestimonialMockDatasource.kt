package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.Testimonial
import br.com.lactarehub.domain.entity.TestimonialType

object TestimonialMockDatasource {

    val items: MutableList<Testimonial> = mutableListOf(
        Testimonial(
            id = "dep-marina-costa",
            authorName = "Marina Costa",
            city = "São Paulo",
            state = "SP",
            message = "Doar foi a forma mais bonita de prolongar o cuidado que sinto " +
                "pelo meu filho — agora chega a outras famílias.",
            type = TestimonialType.RECORRENTE,
            avatarGradientIndex = 0,
        ),
        Testimonial(
            id = "dep-joana-ribeiro",
            authorName = "Joana Ribeiro",
            city = "Guarulhos",
            state = "SP",
            message = "A coleta domiciliar tornou tudo simples. Em poucas semanas eu " +
                "já estava na minha terceira doação.",
            type = TestimonialType.RECORRENTE,
            avatarGradientIndex = 1,
        ),
        Testimonial(
            id = "dep-bia-fernandes",
            authorName = "Bia Fernandes",
            city = "Osasco",
            state = "SP",
            message = "Saber que minhas gotinhas alimentam um bebê internado mudou " +
                "minha rotina. Cada gota conta mesmo.",
            type = TestimonialType.PRIMEIRA_DOACAO,
            avatarGradientIndex = 2,
        ),
        Testimonial(
            id = "dep-ana-paula",
            authorName = "Ana Paula Menezes",
            city = "Santo André",
            state = "SP",
            message = "Cheguei insegura, achando que produzia pouco. A equipe me " +
                "acolheu e mostrou que pequenas quantidades já fazem diferença.",
            type = TestimonialType.PRIMEIRA_DOACAO,
            avatarGradientIndex = 5,
        ),
        Testimonial(
            id = "dep-cris-tavares",
            authorName = "Cris Tavares",
            city = "São Paulo",
            state = "SP",
            message = "É bonito ver o trajeto da minha doação até o bebê. A timeline " +
                "na área da doadora me emociona toda vez.",
            type = TestimonialType.RECORRENTE,
            avatarGradientIndex = 3,
        ),
        Testimonial(
            id = "dep-leticia-m",
            authorName = "Letícia Machado",
            city = "São Bernardo",
            state = "SP",
            message = "Indiquei três amigas. A rede do Lactare cresce no boca a boca, " +
                "sem complicação.",
            type = TestimonialType.PRIMEIRA_DOACAO,
            avatarGradientIndex = 4,
        ),
    )
}
