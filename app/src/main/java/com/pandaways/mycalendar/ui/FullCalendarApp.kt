package com.pandaways.mycalendar.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pandaways.mycalendar.ui.theme.MyCalendarTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Created by meyta.taliti on 20/05/23.
 */
@Preview(showSystemUi = true)
@Composable
fun FullCalendarAppPreview() {
    MyCalendarTheme {
        FullCalendarApp(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun FullCalendarApp(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val dataSource = CalendarDataSource()
    var data by remember { mutableStateOf(dataSource.getTodayCalendar()) }
    Column(modifier = modifier.fillMaxSize()) {
        FullCalendarHeader(
            data = data,
            onPrevClickListener = { startDate ->
                data = dataSource.getPreviousMonth(startDate)
            },
            onNextClickListener = { endDate ->
                data = dataSource.getNextMonth(endDate)
            }
        )
        FullCalendarContent(data = data) { date ->
            Toast.makeText(
                context,
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun FullCalendarHeader(
    data: FullCalendarUiModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
) {
    Row {
        Text(
            text = data.yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onPrevClickListener(data.startDate)
        }) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Back"
            )
        }
        IconButton(onClick = {
            onNextClickListener(data.endDate)
        }) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun FullCalendarContent(
    data: FullCalendarUiModel,
    onDateClickListener: (LocalDate) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 48.dp)) {
        items(data.visibleDates.size) {index ->
            FullCalendarContentItem(
                date = data.visibleDates[index],
                onDateClickListener
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullCalendarContentItem(
    date: LocalDate,
    onClickListener: (LocalDate) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable {
                onClickListener(date)
            }
        ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.format(DateTimeFormatter.ofPattern("E")),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

