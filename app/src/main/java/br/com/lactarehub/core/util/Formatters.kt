package br.com.lactarehub.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

object Formatters {

    private val months = listOf(
        "jan", "fev", "mar", "abr", "mai", "jun",
        "jul", "ago", "set", "out", "nov", "dez",
    )

    private val weekdays = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")

    fun volume(milliliters: Int): String = "$milliliters ml"

    fun liters(milliliters: Int): String {
        val value = milliliters / 1000.0
        return "${String.format(java.util.Locale.US, "%.1f", value).replace('.', ',')} L"
    }

    fun thousands(value: Int): String {
        val digits = value.toString()
        val builder = StringBuilder()
        for (i in digits.indices) {
            if (i > 0 && (digits.length - i) % 3 == 0) builder.append('.')
            builder.append(digits[i])
        }
        return builder.toString()
    }

    fun shortDate(date: LocalDate): String = "${date.dayOfMonth} ${months[date.monthValue - 1]}"

    fun shortDate(date: LocalDateTime): String = shortDate(date.toLocalDate())

    fun paddedDate(date: LocalDate): String =
        "${date.dayOfMonth.toString().padStart(2, '0')}/${months[date.monthValue - 1]}"

    fun paddedDate(date: LocalDateTime): String = paddedDate(date.toLocalDate())

    fun monthBadge(date: LocalDate): String = months[date.monthValue - 1].uppercase()

    fun monthBadge(date: LocalDateTime): String = monthBadge(date.toLocalDate())

    fun weekdayAndDate(date: LocalDate): String =
        "${weekdays[date.dayOfWeek.value - 1]}, ${shortDate(date)}"

    fun weekdayAndDate(date: LocalDateTime): String = weekdayAndDate(date.toLocalDate())

    fun weekdayShort(date: LocalDate): String = weekdays[date.dayOfWeek.value - 1]

    fun daysUntil(date: LocalDate, reference: LocalDate): String {
        val days = ChronoUnit.DAYS.between(reference, date)
        return when {
            days < 0 -> "já passou"
            days == 0L -> "hoje"
            days == 1L -> "amanhã"
            else -> "em $days dias"
        }
    }

    fun daysUntil(date: LocalDateTime, reference: LocalDate): String =
        daysUntil(date.toLocalDate(), reference)

    fun daysAgo(days: Int): String = when (abs(days)) {
        0 -> "hoje"
        1 -> "ontem"
        else -> "$days dias atrás"
    }

    fun initials(fullName: String): String {
        val parts = fullName.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        if (parts.isEmpty()) return "?"
        if (parts.size == 1) return parts.first().take(1).uppercase()
        return (parts.first().take(1) + parts.last().take(1)).uppercase()
    }

    fun oneDecimal(value: Double): String = String.format(java.util.Locale.US, "%.1f", value)
}
