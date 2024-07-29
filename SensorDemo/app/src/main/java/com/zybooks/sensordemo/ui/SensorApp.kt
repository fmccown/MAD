package com.zybooks.sensordemo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SensorApp(
   viewModel: SensorViewModel = viewModel(
      factory = SensorViewModel.Factory
   )
) {
   val proximity = viewModel.proximity

   Text(
      text = "Proximity: $proximity cm",
      fontSize = 34.sp,
      modifier = Modifier.padding(16.dp)
   )

   LifecycleResumeEffect(Unit) {
      viewModel.startListening()

      onPauseOrDispose {
         viewModel.stopListening()
      }
   }
}