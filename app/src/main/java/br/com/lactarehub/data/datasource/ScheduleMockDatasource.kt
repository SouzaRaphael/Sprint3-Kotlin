package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.CollectionMode
import br.com.lactarehub.domain.entity.CollectionSchedule
import java.time.LocalDate
import java.time.LocalDateTime

object ScheduleMockDatasource {

    val today: LocalDate = LocalDate.of(2026, 5, 4)

    val demoNext = CollectionSchedule(
        id = "agd-0508",
        scheduledAt = LocalDateTime.of(2026, 5, 8, 10, 0),
        timeWindow = "10h às 12h",
        mode = CollectionMode.DOMICILIAR,
        place = "Vila Mariana",
        isConfirmed = false,
        referenceToday = today,
    )

    val availableWindows: List<String> = listOf(
        "08h às 10h",
        "10h às 12h",
        "14h às 16h",
        "16h às 18h",
    )
}
