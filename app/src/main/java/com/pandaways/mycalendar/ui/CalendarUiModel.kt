package com.pandaways.mycalendar.ui

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Created by meyta.taliti on 20/05/23.
 */
data class CalendarUiModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
) {

    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean
    ) {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))
    }
}