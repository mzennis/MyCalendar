package com.pandaways.mycalendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pandaways.mycalendar.R
import com.pandaways.mycalendar.data.CalendarUiState
import com.pandaways.mycalendar.ui.theme.MyCalendarTheme
import com.pandaways.mycalendar.ui.util.DateUtil
import com.pandaways.mycalendar.ui.util.getDisplayName
import java.time.YearMonth

/**
 * Created by meyta.taliti on 20/05/23.
 */
@Preview(showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    MyCalendarTheme {
        CalendarApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarApp(
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ))
        }
    ) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            CalendarWidget(
                days = DateUtil.daysOfWeek,
                yearMonth = uiState.yearMonth,
                dates = uiState.dates,
                onPreviousMonthButtonClicked = { prevMonth ->
                    viewModel.toPreviousMonth(prevMonth)
                },
                onNextMonthButtonClicked = { nextMonth ->
                    viewModel.toNextMonth(nextMonth)
                },
                onDateClickListener = {
                    // TODO("set on date click listener")
                }
            )
        }
    }
}

@Composable
fun CalendarWidget(
    days: Array<String>,
    yearMonth: YearMonth,
    dates: List<CalendarUiState.Date>,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            repeat(days.size) {
                val item = days[it]
                DayItem(item, modifier = Modifier.weight(1f))
            }
        }
        Header(
            yearMonth = yearMonth,
            onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
            onNextMonthButtonClicked = onNextMonthButtonClicked
        )
        Content(
            dates = dates,
            onDateClickListener = onDateClickListener
        )
    }
}

@Composable
fun Header(
    yearMonth: YearMonth,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
) {
    Row {
        IconButton(onClick = {
            onPreviousMonthButtonClicked.invoke(yearMonth.minusMonths(1))
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = stringResource(id = R.string.back)
            )
        }
        Text(
            text = yearMonth.getDisplayName(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onNextMonthButtonClicked.invoke(yearMonth.plusMonths(1))
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.next)
            )
        }
    }
}

@Composable
fun DayItem(day: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

@Composable
fun Content(
    dates: List<CalendarUiState.Date>,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column {
        var index = 0
        repeat(6) {
            Row {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    ContentItem(
                        date = item,
                        onClickListener = onDateClickListener,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
}

@Composable
fun ContentItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (date.isSelected) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    Color.Transparent
                }
            )
            .clickable {
                onClickListener(date)
            }
    ) {
        Text(
            text = date.dayOfMonth,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

