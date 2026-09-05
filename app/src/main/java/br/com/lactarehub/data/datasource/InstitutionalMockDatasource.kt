package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.HowItWorksIcon
import br.com.lactarehub.domain.entity.HowItWorksStep
import br.com.lactarehub.domain.entity.ImpactStats

/** Conteúdo institucional apresentado na landing pública. */
object InstitutionalMockDatasource {

    val stats = ImpactStats(
        litersCollected = 847,
        collectionYear = 2024,
        babiesAssisted = 8470,
        donorsInNetwork = 1284,
        connectedBanks = 17,
        states = 5,
        highlightedStates = listOf("MS", "AL", "RJ", "SP"),
    )

    val howItWorks: List<HowItWorksStep> = listOf(
        HowItWorksStep(
            number = "01",
            title = "Cadastre-se",
            description = "2 minutos pelo celular, com triagem digital guiada por " +
                "enfermeiros do BLH mais próximo.",
            icon = HowItWorksIcon.PESSOA,
        ),
        HowItWorksStep(
            number = "02",
            title = "Receba a coleta",
            description = "Coleta domiciliar agendada via WhatsApp ou entregue em " +
                "qualquer ponto da rede.",
            icon = HowItWorksIcon.LOCAL,
        ),
        HowItWorksStep(
            number = "03",
            title = "Acompanhe o impacto",
            description = "Cada mL rastreado: veja quantos bebês foram atendidos com " +
                "a sua doação.",
            icon = HowItWorksIcon.CORACAO,
        ),
    )
}
