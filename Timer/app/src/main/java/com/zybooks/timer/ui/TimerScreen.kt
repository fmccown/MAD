package com.zybooks.timer.ui

import android.widget.NumberPicker
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.timer.ui.theme.TimerTheme
import java.text.DecimalFormat
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TimerScreen(
   modifier: Modifier = Modifier,
   timerViewModel: TimerViewModel = viewModel()
) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      Box(
         modifier = modifier.padding(20.dp).size(240.dp),
         contentAlignment = Alignment.Center
      ) {
         /*
         CircularProgressIndicator(
            progress = timerViewModel.currentTime.toFloat() / timerViewModel.totalTime,
            modifier = modifier.size(240.dp),
            color = Color.Magenta,
            strokeWidth = 20.dp,
         )*/
         if (timerViewModel.isRunning) {
            AnimatedCircularProgressIndicator(
               timeDuration = timerViewModel.totalTime.toInt()
            )
         }
         Text(
            text = timerText(timerViewModel.currentTime),
            fontSize = 40.sp,
         )
      }
      TimePicker(
         hour = timerViewModel.selectedHour,
         min = timerViewModel.selectedMinute,
         sec = timerViewModel.selectedSecond,
         onPick = timerViewModel::selectTime
      )
      Row {
         if (timerViewModel.isRunning) {
            Button(
               onClick = timerViewModel::cancelTimer,
               modifier = modifier.padding(50.dp)
            ) {
               Text("Cancel")
            }
         } else {
            Button(
               onClick = timerViewModel::startTimer,
               modifier = modifier.padding(top = 50.dp)
            ) {
               Text("Start")
            }
         }
      }
   }
}

@Composable
fun AnimatedCircularProgressIndicator(
   indicatorProgress: Float = 0f,
   timeDuration: Int = 1000
) {
   var progress by remember { mutableFloatStateOf(1f) }
   val progressAnimation by animateFloatAsState(
      targetValue = progress,
      animationSpec = tween(
         durationMillis = timeDuration,
         easing = LinearEasing
      ),
      label = "Progress indicator",
   )

   CircularProgressIndicator(
      progress = progressAnimation,
      modifier = Modifier.size(240.dp),
      color = Color.Magenta,
      strokeWidth = 20.dp,
   )

   LaunchedEffect(true) {
      progress = indicatorProgress
   }
}

fun timerText(timeInMillis: Long): String {
   val duration: Duration = timeInMillis.milliseconds
   return String.format(
      "%02d:%02d:%02d",
      duration.inWholeHours,
      duration.inWholeMinutes % 60,
      duration.inWholeSeconds % 60)
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
   TimerTheme {
      TimerScreen()
   }
}

@Composable
fun TimePicker(
   hour: Int = 0,
   min: Int = 0,
   sec: Int = 0,
   onPick: (Int, Int, Int) -> Unit = { _: Int, _: Int, _: Int -> }
) {
   // Values must be remembered for calls to onPick()
   var hourVal by remember { mutableIntStateOf(hour) }
   var minVal by remember { mutableIntStateOf(min) }
   var secVal by remember { mutableIntStateOf(sec) }

   println("TimePicker: $hourVal : $minVal : $secVal")

   Row(
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
   ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
         Text("Hours")
         NumberPicker(
            initVal = hourVal,
            maxVal = 99,
            onNumberSelected = {
               hourVal = it
               onPick(hourVal, minVal, secVal)
            }
         )
      }
      Column(
         horizontalAlignment = Alignment.CenterHorizontally,
         modifier = Modifier.padding(start = 16.dp, end = 16.dp)
      ) {
         Text("Minutes")
         NumberPicker(
            initVal = minVal,
            onNumberSelected = {
               minVal = it
               onPick(hourVal, minVal, secVal)
            }
         )
      }
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
         Text("Seconds")
         NumberPicker(
            initVal = secVal,
            onNumberSelected = {
               secVal = it
               onPick(hourVal, minVal, secVal)
            }
         )
      }
   }
}

@Composable
fun NumberPicker(
   initVal: Int = 0,
   minVal: Int = 0,
   maxVal: Int = 59,
   onNumberSelected: (Int) -> Unit = {}
) {
   val numFormat = NumberPicker.Formatter { i: Int ->
      DecimalFormat("00").format(i)
   }

   println("NumberPicker: initVal = $initVal")
   AndroidView(
      factory = { context ->
         NumberPicker(context).apply {
            setOnValueChangedListener { numberPicker, oldVal, newVal -> onNumberSelected(newVal) }
            minValue = minVal
            maxValue = maxVal
            value = initVal
            setFormatter(numFormat)
         }
      }
   )
}