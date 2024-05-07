package com.zybooks.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zybooks.countdowntimer.ui.TimerScreen
import com.zybooks.countdowntimer.ui.TimerViewModel
import com.zybooks.countdowntimer.ui.theme.CountdownTimerTheme

class MainActivity : ComponentActivity() {

   private val timerViewModel = TimerViewModel()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         CountdownTimerTheme(dynamicColor = false) {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               TimerScreen(timerViewModel = timerViewModel)
            }
         }
      }
   }
}
