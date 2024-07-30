package com.zybooks.compass.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.compass.R

@Composable
fun CompassApp(
   viewModel: CompassViewModel = viewModel(
      factory = CompassViewModel.Factory
   )
) {
   Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
   ) {
      Image(
         painter = painterResource(id = R.drawable.compass_rose),
         contentDescription = "Compass",
         modifier = Modifier
            .size(200.dp)
            .rotate(viewModel.rotation)
      )
   }
   LifecycleResumeEffect(Unit) {
      viewModel.startListening()

      onPauseOrDispose {
         viewModel.stopListening()
      }
   }
}
