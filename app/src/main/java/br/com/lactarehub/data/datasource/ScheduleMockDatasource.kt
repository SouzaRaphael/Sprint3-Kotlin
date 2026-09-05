package br.com.lactarehub.data.datasource

import br.com.lactarehub.domain.entity.CollectionMode
import br.com.lactarehub.domain.entity.CollectionSchedule
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Agenda de coletas de exemplo.
 *
 * É catálogo: a coleta de fato marcada vive no `SessionMockDatasource`, que
 * a substitui ou zera conforme a pessoa tenha entrado pelo login ou pelo
 * cadastro.
 */
object ScheduleMockDatasource {

    /** Data de referência do protótipo — mantém as telas coerentes entre si. */
    val today: LocalDate = LocalDate.of(2026, 5, 4)

    /** Coleta já agendada da persona de demonstração. */
    val demoNext = CollectionSchedule(
        id = "agd-0508",
        scheduledAt = LocalDateTime.of(2026, 5, 8, 10, 0),
        timeWindow = "10h às 12h",
        mode = CollectionMode.DOMICILIAR,
        place = "Vila Mariana",
        isConfirmed = false,
        referenceToday = today,
    )

    /** Janelas de horário oferecidas no formulário de agendamento. */
    val availableWindows: List<String> = listOf(
        "08h às 10h",
        "10h às 12h",
        "14h às 16h",
        "16h às 18h",
    )
}
