package com.zybooks.countdowntimer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
   private var timerJob: Job? = null

   // Values selected in time picker
   var selectedHour by mutableIntStateOf(0)
      private set
   var selectedMinute by mutableIntStateOf(0)
      private set
   var selectedSecond by mutableIntStateOf(0)
      private set

   // Total milliseconds when timer starts
   var totalMillis by mutableLongStateOf(0L)
      private set

   // Time that remains
   var remainingMillis by mutableLongStateOf(0L)
      private set

   // Timer's running status
   var isRunning by mutableStateOf(false)
      private set

   fun selectTime(hour: Int, min: Int, sec: Int) {
      selectedHour = hour
      selectedMinute = min
      selectedSecond = sec
   }

   fun startTimer() {
      // Convert hours, minutes, and seconds to milliseconds
      totalMillis = (selectedHour * 60 * 60 + selectedMinute * 60 + selectedSecond) * 1000L

      // Start coroutine that makes the timer count down
      if (totalMillis > 0) {
         isRunning = true
         remainingMillis = totalMillis

         timerJob = viewModelScope.launch {
            while (remainingMillis > 0) {
               delay(1000)
               remainingMillis -= 1000
            }

            isRunning = false
         }
      }
   }

   fun cancelTimer() {
      if (isRunning) {
         timerJob?.cancel()
         isRunning = false
         remainingMillis = 0
      }
   }

   override fun onCleared() {
      super.onCleared()
      timerJob?.cancel()
   }
}