package br.com.lactarehub

import br.com.lactarehub.core.util.Formatters
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class FormattersTest {

    @Test
    fun `volume usa a unidade em mililitros`() {
        assertEquals("460 ml", Formatters.volume(460))
    }

    @Test
    fun `litros usam virgula decimal`() {
        assertEquals("3,2 L", Formatters.liters(3200))
        assertEquals("0,3 L", Formatters.liters(250))
    }

    @Test
    fun `milhares usam ponto como separador`() {
        assertEquals("1.284", Formatters.thousands(1284))
        assertEquals("847", Formatters.thousands(847))
        assertEquals("8.470", Formatters.thousands(8470))
    }

    @Test
    fun `datas curtas seguem o padrao do design`() {
        val date = LocalDate.of(2026, 5, 8)
        assertEquals("8 mai", Formatters.shortDate(date))
        assertEquals("08/mai", Formatters.paddedDate(date))
        assertEquals("MAI", Formatters.monthBadge(date))
        assertEquals("Sex, 8 mai", Formatters.weekdayAndDate(date))
    }

    @Test
    fun `dias restantes viram linguagem natural`() {
        val reference = LocalDate.of(2026, 5, 4)
        assertEquals("hoje", Formatters.daysUntil(LocalDate.of(2026, 5, 4), reference))
        assertEquals("amanhã", Formatters.daysUntil(LocalDate.of(2026, 5, 5), reference))
        assertEquals("em 4 dias", Formatters.daysUntil(LocalDate.of(2026, 5, 8), reference))
        assertEquals("já passou", Formatters.daysUntil(LocalDate.of(2026, 5, 1), reference))
    }

    @Test
    fun `dias desde a ultima doacao viram linguagem natural`() {
        assertEquals("hoje", Formatters.daysAgo(0))
        assertEquals("ontem", Formatters.daysAgo(1))
        assertEquals("3 dias atrás", Formatters.daysAgo(3))
    }

    @Test
    fun `iniciais usam o primeiro e o ultimo nome`() {
        assertEquals("GR", Formatters.initials("Giovana Aparecida Ramos"))
        assertEquals("M", Formatters.initials("Marina"))
        assertEquals("?", Formatters.initials("   "))
    }
}
