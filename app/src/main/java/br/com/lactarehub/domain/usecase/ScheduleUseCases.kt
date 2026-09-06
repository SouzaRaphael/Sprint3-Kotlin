package br.com.lactarehub.domain.usecase

import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.repository.ScheduleRepository
import java.time.LocalDate

class GetNextCollection(private val repository: ScheduleRepository) {
    suspend operator fun invoke(): CollectionSchedule? = repository.getNextCollection()
}

class ConfirmCollection(private val repository: ScheduleRepository) {
    suspend operator fun invoke(): CollectionSchedule? = repository.confirm()
}

class ScheduleCollection(private val repository: ScheduleRepository) {
    suspend operator fun invoke(schedule: CollectionSchedule): CollectionSchedule =
        repository.create(schedule)
}

class GetAvailableWindows(private val repository: ScheduleRepository) {
    operator fun invoke(): List<String> = repository.listAvailableWindows()
}

class GetReferenceDate(private val repository: ScheduleRepository) {
    operator fun invoke(): LocalDate = repository.referenceToday()
}
