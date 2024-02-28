package com.pandaways.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pandaways.mycalendar.ui.CalendarApp
import com.pandaways.mycalendar.ui.CalendarViewModel
import com.pandaways.mycalendar.ui.theme.MyCalendarTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {
                CalendarApp()
            }
        }
    }
}
