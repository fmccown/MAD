package com.zybooks.photoexpress.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
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
      if (uiState.photoVisible) {
         PhotoScreen(
            photoUri = uiState.photoUri,
            colorFilter = uiState.colorFilter,
            brightness = uiState.brightness,
            onBrightnessChange = {
               viewModel.changeBrightness(it)
            },
            modifier = Modifier.padding(innerPadding)
         )
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

@Composable
fun PhotoScreen(
   photoUri: Uri,
   colorFilter: ColorFilter,
   brightness: Float,
   onBrightnessChange: (Float) -> Unit,
   modifier: Modifier = Modifier,
) {
   Column(
      verticalArrangement = Arrangement.Center,
      modifier = modifier
   ) {
      AsyncImage(
         model = photoUri,
         contentDescription = null,
         modifier = Modifier.fillMaxHeight(0.9f),
         colorFilter = colorFilter
      )
      Slider(
         value = brightness,
         valueRange = 0f..200f,
         onValueChange = onBrightnessChange
      )
   }
}