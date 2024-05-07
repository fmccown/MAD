package com.zybooks.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
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

   override fun onStop() {
      super.onStop()

      // Start TimerWorker if the timer is running
      if (timerViewModel.isRunning) {
         startWorker(timerViewModel.remainingMillis)
      }
   }

   private fun startWorker(millisRemain: Long) {
      val timerWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<TimerWorker>()
         .setInputData(
            workDataOf(
               KEY_MILLIS_REMAINING to millisRemain
            )
         ).build()

      println("Starting worker")
      WorkManager.getInstance(applicationContext).enqueue(timerWorkRequest)
   }
}
