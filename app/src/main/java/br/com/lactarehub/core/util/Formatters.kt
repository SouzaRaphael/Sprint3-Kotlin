package br.com.lactarehub.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

/** Formatações de exibição usadas em mais de uma tela. */
object Formatters {

    private val months = listOf(
        "jan", "fev", "mar", "abr", "mai", "jun",
        "jul", "ago", "set", "out", "nov", "dez",
    )

    private val weekdays = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")

    /** `460` → `460 ml`. */
    fun volume(milliliters: Int): String = "$milliliters ml"

    /** `3200` → `3,2 L`. */
    fun liters(milliliters: Int): String {
        val value = milliliters / 1000.0
        return "${String.format(java.util.Locale.US, "%.1f", value).replace('.', ',')} L"
    }

    /** `1284` → `1.284`. */
    fun thousands(value: Int): String {
        val digits = value.toString()
        val builder = StringBuilder()
        for (i in digits.indices) {
            if (i > 0 && (digits.length - i) % 3 == 0) builder.append('.')
            builder.append(digits[i])
        }
        return builder.toString()
    }

    /** `2026-05-08` → `8 mai`. */
    fun shortDate(date: LocalDate): String = "${date.dayOfMonth} ${months[date.monthValue - 1]}"

    fun shortDate(date: LocalDateTime): String = shortDate(date.toLocalDate())

    /** `2026-05-08` → `08/mai`. */
    fun paddedDate(date: LocalDate): String =
        "${date.dayOfMonth.toString().padStart(2, '0')}/${months[date.monthValue - 1]}"

    fun paddedDate(date: LocalDateTime): String = paddedDate(date.toLocalDate())

    /** Abreviação do mês em maiúsculas, usada no selo de data. */
    fun monthBadge(date: LocalDate): String = months[date.monthValue - 1].uppercase()

    fun monthBadge(date: LocalDateTime): String = monthBadge(date.toLocalDate())

    /** `2026-05-08` → `Sex, 8 mai`. */
    fun weekdayAndDate(date: LocalDate): String =
        "${weekdays[date.dayOfWeek.value - 1]}, ${shortDate(date)}"

    fun weekdayAndDate(date: LocalDateTime): String = weekdayAndDate(date.toLocalDate())

    /** Só o dia da semana: `Sex`. */
    fun weekdayShort(date: LocalDate): String = weekdays[date.dayOfWeek.value - 1]

    /** Diferença em dias a partir de [reference], em linguagem natural. */
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

    /** `3` → `3 dias atrás`. Usado no resumo da jornada. */
    fun daysAgo(days: Int): String = when (abs(days)) {
        0 -> "hoje"
        1 -> "ontem"
        else -> "$days dias atrás"
    }

    /** Iniciais para os avatares em gradiente. */
    fun initials(fullName: String): String {
        val parts = fullName.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        if (parts.isEmpty()) return "?"
        if (parts.size == 1) return parts.first().take(1).uppercase()
        return (parts.first().take(1) + parts.last().take(1)).uppercase()
    }

    /** `4.1` → `4.1` — uma casa decimal, como no design. */
    fun oneDecimal(value: Double): String = String.format(java.util.Locale.US, "%.1f", value)
}
