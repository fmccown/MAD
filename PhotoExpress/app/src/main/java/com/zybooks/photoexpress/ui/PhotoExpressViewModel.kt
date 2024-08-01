package com.zybooks.photoexpress.ui

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LightingColorFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.photoexpress.PhotoExpressApplication
import com.zybooks.photoexpress.data.ImageRepository

class PhotoExpressViewModel(private val imageRepo: ImageRepository) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as PhotoExpressApplication)
            PhotoExpressViewModel(application.imageRepo)
         }
      }
   }

   var photoUri by mutableStateOf(imageRepo.photoUri)
   var colorFilter by mutableStateOf(LightingColorFilter(Color.White, Color.Black))
   var imageVisible by mutableStateOf(false)
   var photoSaved by mutableStateOf(false)

   suspend fun saveAlteredPhoto() {
      imageRepo.saveAlteredPhoto(colorFilter)
      photoSaved = true
   }

   fun photoTaken() {
      imageVisible = true
   }

   fun changeBrightness(brightness: Float) {
      colorFilter = imageRepo.changeBrightness(brightness)
      photoSaved = false
   }

   fun takePhoto() {
      imageVisible = false
      photoSaved = false
      changeBrightness(100f)   // Reset
      imageRepo.createNewPhotoFile()
      photoUri = imageRepo.photoUri
   }
}

data class PhotoExpressUiState(
   val photoUri: Uri = Uri.EMPTY,
   val colorFilter: LightingColorFilter = LightingColorFilter(Color.White, Color.Black),
   val photoVisible: Boolean = false,
   val photoSaved: Boolean = false
)