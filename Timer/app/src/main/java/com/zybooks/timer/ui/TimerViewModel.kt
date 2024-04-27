package com.zybooks.timer.ui

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.ceil

class TimerViewModel : ViewModel() {
   private val timerInterval = 1000L
   private var timer: CountDownTimer? = null

   // Values selected in time picker
   var selectedHour by mutableIntStateOf(0)
      private set
   var selectedMinute by mutableIntStateOf(0)
      private set
   var selectedSecond by mutableIntStateOf(0)
      private set

   // Timer's total time in milliseconds
   var totalTime = 0L
      private set

   // Timer's current time in milliseconds
   var currentTime by mutableLongStateOf(totalTime)
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
      totalTime = (selectedHour * 60L * 60 + selectedMinute * 60 + selectedSecond) * 1000

      timer = object : CountDownTimer(totalTime, timerInterval) {
         override fun onTick(millisUntilFinished: Long) {
            currentTime = (ceil(millisUntilFinished/1000.0) *1000).toLong()
         }

         override fun onFinish() {
            currentTime = 0
            isRunning = false
         }
      }

      timer?.start()
      isRunning = true
   }

   fun cancelTimer() {
      if (isRunning) {
         timer?.cancel()
         isRunning = false
         currentTime = 0
      }
   }

   override fun onCleared() {
      super.onCleared()
      timer?.cancel()
      currentTime = totalTime
   }
}