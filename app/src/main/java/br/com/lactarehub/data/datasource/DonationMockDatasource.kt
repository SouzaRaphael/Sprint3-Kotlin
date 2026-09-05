package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.Donation
import br.com.lactarehub.domain.entity.DonationStatus
import br.com.lactarehub.domain.entity.TrackingStep
import br.com.lactarehub.domain.entity.TrackingStepStatus
import java.time.LocalDate

/**
 * Histórico de doações da persona de demonstração, com as linhas do tempo
 * de rastreio.
 *
 * É catálogo: as doações da sessão atual vêm do `SessionMockDatasource`.
 */
object DonationMockDatasource {

    val demoItems: List<Donation> = listOf(
        Donation(
            code = "LCT-2104",
            collectedAt = LocalDate.of(2026, 4, 27),
            volumeMilliliters = 460,
            status = DonationStatus.EM_ANDAMENTO,
            collectionPlace = "Coleta domiciliar · Vila Mariana",
            destinationHospital = "UTI neonatal do Hospital Pinheiros",
            timeline = listOf(
                TrackingStep(
                    title = "Leite coletado",
                    description = "27/abr · 09:30 · em sua casa",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Em análise",
                    description = "28/abr · Banco Central Lactare",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Aprovado e pasteurizado",
                    description = "29/abr · 460 ml em estoque",
                    status = TrackingStepStatus.ATUAL,
                ),
                TrackingStep(
                    title = "Distribuído ao hospital",
                    description = "Em breve · UTI neonatal parceira",
                    status = TrackingStepStatus.PENDENTE,
                ),
            ),
        ),
        Donation(
            code = "LCT-2087",
            collectedAt = LocalDate.of(2026, 4, 12),
            volumeMilliliters = 380,
            status = DonationStatus.DISTRIBUIDA,
            collectionPlace = "Posto de Coleta Vila Mariana",
            destinationHospital = "UTI neonatal do Hospital Pinheiros",
            timeline = listOf(
                TrackingStep(
                    title = "Leite coletado",
                    description = "12/abr · 10:05 · Posto Vila Mariana",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Em análise",
                    description = "13/abr · Banco Central Lactare",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Aprovado e pasteurizado",
                    description = "14/abr · 380 ml liberados",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Distribuído ao hospital",
                    description = "16/abr · alimentou 3 bebês prematuros",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
            ),
        ),
        Donation(
            code = "LCT-2043",
            collectedAt = LocalDate.of(2026, 3, 30),
            volumeMilliliters = 250,
            status = DonationStatus.DISTRIBUIDA,
            collectionPlace = "Coleta domiciliar · Vila Mariana",
            destinationHospital = "Maternidade Leonor Mendes de Barros",
            timeline = listOf(
                TrackingStep(
                    title = "Leite coletado",
                    description = "30/mar · 08:40 · em sua casa",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Em análise",
                    description = "31/mar · Banco Central Lactare",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Aprovado e pasteurizado",
                    description = "01/abr · 250 ml liberados",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
                TrackingStep(
                    title = "Distribuído ao hospital",
                    description = "03/abr · alimentou 2 bebês prematuros",
                    status = TrackingStepStatus.CONCLUIDA,
                ),
            ),
        ),
    )
}
