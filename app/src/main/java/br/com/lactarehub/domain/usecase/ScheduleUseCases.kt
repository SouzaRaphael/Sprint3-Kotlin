package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.repository.ScheduleRepository
import java.time.LocalDate

/** Próxima coleta agendada da doadora. */
class GetNextCollection(private val repository: ScheduleRepository) {
    suspend operator fun invoke(): CollectionSchedule? = repository.getNextCollection()
}

/** Confirma a presença na coleta já agendada. */
class ConfirmCollection(private val repository: ScheduleRepository) {
    suspend operator fun invoke(): CollectionSchedule? = repository.confirm()
}

/** Registra uma nova coleta a partir do formulário de agendamento. */
class ScheduleCollection(private val repository: ScheduleRepository) {
    suspend operator fun invoke(schedule: CollectionSchedule): CollectionSchedule =
        repository.create(schedule)
}

/** Janelas de horário disponíveis para a coleta. */
class GetAvailableWindows(private val repository: ScheduleRepository) {
    operator fun invoke(): List<String> = repository.listAvailableWindows()
}

/**
 * "Hoje" segundo o protótipo.
 *
 * O formulário de agendamento precisa dessa data mesmo quando não há coleta
 * marcada — que é justamente o caso de quem acabou de se cadastrar.
 */
class GetReferenceDate(private val repository: ScheduleRepository) {
    operator fun invoke(): LocalDate = repository.referenceToday()
}
