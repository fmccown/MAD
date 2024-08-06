package com.zybooks.photoexpress.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.zybooks.photoexpress.R
import kotlinx.coroutines.launch

@Composable
fun PhotoExpressApp(
   viewModel: PhotoExpressViewModel = viewModel(
      factory = PhotoExpressViewModel.Factory
   )
) {
   val uiState by viewModel.uiState.collectAsStateWithLifecycle()
   var sliderPosition by remember { mutableFloatStateOf(100f) }
   val coroutineScope = rememberCoroutineScope()

   val cameraLauncher = rememberLauncherForActivityResult(
         ActivityResultContracts.TakePicture()
      ) { success ->
         if (success) {
            viewModel.photoTaken()
         }
      }

   Scaffold(
      topBar = {
         PhotoExpressTopAppBar(
            onTakePhoto = {
               val photoUri = viewModel.takePhoto()
               sliderPosition = 100f
               cameraLauncher.launch(photoUri)
            },
            onSavePhoto = {
               coroutineScope.launch {
                  viewModel.savePhoto()
               }
            },
            isPhotoSaved = uiState.photoSaved
         )
      }
   ) { innerPadding ->
      Column(
         verticalArrangement = Arrangement.Center,
         modifier = Modifier.padding(innerPadding)
      ) {
         if (uiState.photoVisible) {
            AsyncImage(
               model = uiState.photoUri,
               contentDescription = null,
               modifier = Modifier.fillMaxHeight(0.9f),
               colorFilter = uiState.colorFilter
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoExpressTopAppBar(
   onTakePhoto: () -> Unit = {},
   onSavePhoto: () -> Unit = {},
   isPhotoSaved: Boolean = false
) {
   TopAppBar(
      title = { Text("Photo Express") },
      actions = {
         IconButton(onClick = onTakePhoto) {
            Icon(
               painter = painterResource(R.drawable.camera),
               contentDescription = "Take Photo"
            )
         }
         IconButton(
            onClick = onSavePhoto,
            enabled = !isPhotoSaved
         ) {
            Icon(
               painter = painterResource(R.drawable.save),
               contentDescription = "Save"
            )
         }
      }
   )
}

