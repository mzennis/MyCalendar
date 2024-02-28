package com.pandaways.mycalendar.data

import com.pandaways.mycalendar.ui.util.getDayOfMonthStartingFromMonday
import java.time.LocalDate
import java.time.YearMonth

/**
 * Created by meyta.taliti on 20/05/23.
 */
class CalendarDataSource {

    fun getDates(yearMonth: YearMonth): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                CalendarUiState.Date(
                    dayOfMonth = if (date.monthValue == yearMonth.monthValue) {
                        "${date.dayOfMonth}"
                    } else {
                        "" // Fill with empty string for days outside the current month
                    },
                    isSelected = date.isEqual(LocalDate.now()) && date.monthValue == yearMonth.monthValue
                )
            }
    }
}