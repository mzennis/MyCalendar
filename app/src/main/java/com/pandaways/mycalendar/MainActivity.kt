package com.pandaways.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pandaways.mycalendar.ui.CalendarApp
import com.pandaways.mycalendar.ui.theme.MyCalendarTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {
                Card(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                ) {
                    CalendarApp(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 15.dp),
                        onSelectedDataChange = { newData ->
                            // Trigger an Action Based on the Selected Date
                        }
                    )
                }
            }
        }
    }
}
