package com.zybooks.photoexpress.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PhotoExpressApp(
   viewModel: PhotoExpressViewModel = viewModel(
      factory = PhotoExpressViewModel.Factory
   )
) {
   val cameraLauncher =
      rememberLauncherForActivityResult(
         ActivityResultContracts.TakePicture()
      ) { success ->
         if (success) {
            viewModel.photoTaken()
         }
      }

   var sliderPosition by remember { mutableFloatStateOf(100f) }
   val coroutineScope = rememberCoroutineScope()

   Column(
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.padding(16.dp)
   ) {
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         Button(onClick = {
            viewModel.takePhoto()
            sliderPosition = 100f
            println("viewModel.photoUri = ${viewModel.photoUri}")
            cameraLauncher.launch(viewModel.photoUri)
         }) {
            Text("Take Picture")
         }
         if (viewModel.imageVisible) {
            Button(
               onClick = {
                  coroutineScope.launch {
                     viewModel.saveAlteredPhoto()
                  }
               },
               enabled = !viewModel.photoSaved
            ) {
               Text("Save")
            }
         }
      }

      if (viewModel.imageVisible) {
         Image(
            painter = rememberAsyncImagePainter(viewModel.photoUri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = viewModel.colorFilter
         )

         Slider(
            value = sliderPosition,
            valueRange = 0f..200f,
            onValueChange = {
               sliderPosition = it
               viewModel.changeBrightness(sliderPosition)
            },
            modifier = Modifier.weight(1f)
         )
      }
      else {
         Spacer(Modifier.weight(1f))
      }
   }
}


