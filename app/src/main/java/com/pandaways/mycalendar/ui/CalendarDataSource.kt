package com.pandaways.mycalendar.ui

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Created by meyta.taliti on 20/05/23.
 */
class CalendarDataSource {

    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getPreviousMonth(firstDayOfMonth: LocalDate): FullCalendarUiModel {
        val prevDay = firstDayOfMonth.minusDays(1)
        val prevMonth = YearMonth.of(prevDay.year, prevDay.month)
        return FullCalendarUiModel(
            yearMonth = prevMonth,
            visibleDates = getDatesInBetween(
                prevMonth.atDay(1),
                firstDayOfMonth
            )
        )
    }

    fun getNextMonth(lastDayOfMonth: LocalDate): FullCalendarUiModel {
        val nextDay = lastDayOfMonth.plusDays(1)
        val nextMonth = YearMonth.of(nextDay.year, nextDay.month)
        return FullCalendarUiModel(
            yearMonth = nextMonth,
            visibleDates = getDatesBetween(
                nextMonth.atDay(1),
                nextMonth.plusMonths(1).atDay(1)
            )
        )
    }

    fun getTodayCalendar(): FullCalendarUiModel {
        val yearMonth = YearMonth.now()
        val firstOfMonth = yearMonth.atDay(1)
        val firstOfFollowingMonth = yearMonth.plusMonths(1).atDay(1)
        return FullCalendarUiModel(
            yearMonth = yearMonth,
            visibleDates = getDatesBetween(
                firstOfMonth,
                firstOfFollowingMonth
            )
        )
    }

    private fun getDatesInBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }.limit(numOfDays).collect(Collectors.toList())
    }

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}