package com.pandaways.mycalendar.ui

import androidx.compose.runtime.Stable
import java.time.LocalDate
import java.time.YearMonth

/**
 * Created by meyta.taliti on 20/05/23.
 */
@Stable
data class FullCalendarUiModel(
    val yearMonth: YearMonth,
    val visibleDates: List<LocalDate>
) {

    val startDate: LocalDate = visibleDates.first()
    val endDate: LocalDate = visibleDates.last()
}