package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionPointType

/**
 * Pontos da rede exibidos no mapa da aba "Pontos".
 *
 * Instituições reais da Rede Brasileira de Bancos de Leite Humano, com
 * distâncias e horários simulados para esta Sprint.
 */
object CollectionPointMockDatasource {

    val items: List<CollectionPoint> = listOf(
        CollectionPoint(
            id = "blh-iff",
            name = "BLH IFF — Fernandes Figueira",
            type = CollectionPointType.BLH,
            distanceKm = 1.2,
            openingHours = "08h-18h",
            address = "Av. Rui Barbosa, 716 — Flamengo",
            neighborhood = "Flamengo",
            phone = "(21) 2554-1700",
            isOpenNow = true,
            mapX = 0.34f,
            mapY = 0.28f,
        ),
        CollectionPoint(
            id = "blh-leonor-barros",
            name = "BLH Maternidade Leonor Mendes de Barros",
            type = CollectionPointType.BLH,
            distanceKm = 2.6,
            openingHours = "07h-19h",
            address = "Av. Celso Garcia, 2477 — Belenzinho",
            neighborhood = "Belenzinho",
            phone = "(11) 2292-0977",
            isOpenNow = true,
            mapX = 0.66f,
            mapY = 0.40f,
        ),
        CollectionPoint(
            id = "blh-hu-usp",
            name = "BLH Hospital Universitário da USP",
            type = CollectionPointType.BLH,
            distanceKm = 4.1,
            openingHours = "08h-17h",
            address = "Av. Prof. Lineu Prestes, 2565 — Butantã",
            neighborhood = "Butantã",
            phone = "(11) 3091-9200",
            isOpenNow = false,
            mapX = 0.20f,
            mapY = 0.62f,
        ),
        CollectionPoint(
            id = "posto-vila-mariana",
            name = "Posto de Coleta Vila Mariana",
            type = CollectionPointType.POSTO_DE_COLETA,
            distanceKm = 0.9,
            openingHours = "08h-16h",
            address = "Rua Vergueiro, 1492 — Vila Mariana",
            neighborhood = "Vila Mariana",
            phone = "(11) 5085-3300",
            isOpenNow = true,
            mapX = 0.52f,
            mapY = 0.55f,
        ),
        CollectionPoint(
            id = "posto-ubs-pinheiros",
            name = "Posto de Coleta UBS Pinheiros",
            type = CollectionPointType.POSTO_DE_COLETA,
            distanceKm = 3.4,
            openingHours = "07h-16h",
            address = "Rua Artur de Azevedo, 859 — Pinheiros",
            neighborhood = "Pinheiros",
            phone = "(11) 3081-1140",
            isOpenNow = true,
            mapX = 0.78f,
            mapY = 0.68f,
        ),
        CollectionPoint(
            id = "domiciliar-zona-sul",
            name = "Coleta domiciliar — Zona Sul",
            type = CollectionPointType.COLETA_DOMICILIAR,
            distanceKm = 0.0,
            openingHours = "09h-17h",
            address = "Atende Vila Mariana, Saúde, Ipiranga e Moema",
            neighborhood = "Zona Sul",
            phone = "(11) 98876-4021",
            isOpenNow = true,
            mapX = 0.42f,
            mapY = 0.78f,
        ),
        CollectionPoint(
            id = "domiciliar-zona-leste",
            name = "Coleta domiciliar — Zona Leste",
            type = CollectionPointType.COLETA_DOMICILIAR,
            distanceKm = 0.0,
            openingHours = "09h-17h",
            address = "Atende Tatuapé, Penha, Belenzinho e Mooca",
            neighborhood = "Zona Leste",
            phone = "(11) 98876-4088",
            isOpenNow = false,
            mapX = 0.86f,
            mapY = 0.22f,
        ),
    )
}
