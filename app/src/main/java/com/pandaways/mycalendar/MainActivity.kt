package com.pandaways.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pandaways.mycalendar.ui.CalendarApp
import com.pandaways.mycalendar.ui.theme.MyCalendarTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {
                CalendarApp(
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
