package com.zybooks.countdowntimer.ui

data class TimerUiState (
   val selectedHour: Int = 0,
   val selectedMinute: Int = 0,
   val selectedSecond: Int = 0,
   val totalMillis: Long = 0,
   val remainingMillis: Long = 0,
   val isRunning: Boolean = false
)
