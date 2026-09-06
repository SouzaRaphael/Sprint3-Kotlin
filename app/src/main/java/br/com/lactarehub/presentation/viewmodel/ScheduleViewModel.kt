package br.com.lactarehub.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lactarehub.core.di.ServiceLocator
import br.com.lactarehub.domain.entity.CollectionMode
import br.com.lactarehub.domain.entity.CollectionPoint
import br.com.lactarehub.domain.entity.CollectionSchedule
import br.com.lactarehub.domain.entity.Donor
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleViewModel : ViewModel() {

    var current by mutableStateOf<CollectionSchedule?>(null)
        private set
    var donor by mutableStateOf<Donor?>(null)
        private set
    var points by mutableStateOf<List<CollectionPoint>>(emptyList())
        private set
    var availableWindows by mutableStateOf<List<String>>(emptyList())
        private set

    private var referenceToday: LocalDate = LocalDate.now()

    var mode by mutableStateOf(CollectionMode.DOMICILIAR)
        private set
    var date by mutableStateOf<LocalDate?>(null)
        private set
    var timeWindow by mutableStateOf<String?>(null)
        private set
    var selectedPoint by mutableStateOf<CollectionPoint?>(null)
        private set
    var notes by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(true)
        private set
    var isSubmitting by mutableStateOf(false)
        private set

    val requiresPoint: Boolean get() = mode != CollectionMode.DOMICILIAR

    val canSubmit: Boolean
        get() = date != null && timeWindow != null && (!requiresPoint || selectedPoint != null)

    val selectableDates: List<LocalDate>
        get() = (1..14).map { referenceToday.plusDays(it.toLong()) }

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            isLoading = true
            current = ServiceLocator.getNextCollection()
            donor = ServiceLocator.getDonorProfile()
            points = ServiceLocator.listCollectionPoints()
            availableWindows = ServiceLocator.getAvailableWindows()
            referenceToday = ServiceLocator.getReferenceDate()
            mode = current?.mode ?: CollectionMode.DOMICILIAR
            isLoading = false
        }
    }

    fun selectMode(value: CollectionMode) {
        mode = value
        if (!requiresPoint) selectedPoint = null
    }

    fun selectDate(value: LocalDate) {
        date = value
    }

    fun selectTimeWindow(value: String) {
        timeWindow = value
    }

    fun selectPoint(point: CollectionPoint) {
        selectedPoint = point
    }

    fun updateNotes(value: String) {
        notes = value
    }

    fun submit(onScheduled: (CollectionSchedule) -> Unit) {
        val selectedDate = date
        val window = timeWindow
        if (!canSubmit || selectedDate == null || window == null) return

        viewModelScope.launch {
            isSubmitting = true

            val startHour = window.take(2).toIntOrNull() ?: 10
            val place = if (requiresPoint) {
                selectedPoint?.name.orEmpty()
            } else {
                donor?.neighborhood.orEmpty()
            }

            val schedule = ServiceLocator.scheduleCollection(
                CollectionSchedule(
                    id = "agd-${selectedDate.monthValue}${selectedDate.dayOfMonth}-$startHour",
                    scheduledAt = LocalDateTime.of(
                        selectedDate.year,
                        selectedDate.monthValue,
                        selectedDate.dayOfMonth,
                        startHour,
                        0,
                    ),
                    timeWindow = window,
                    mode = mode,
                    place = place,
                    isConfirmed = true,
                    referenceToday = referenceToday,
                    notes = notes,
                ),
            )

            current = schedule
            isSubmitting = false
            onScheduled(schedule)
        }
    }
}
