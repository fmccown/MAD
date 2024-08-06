package com.zybooks.photoexpress.ui

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LightingColorFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.photoexpress.PhotoExpressApplication
import com.zybooks.photoexpress.data.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PhotoExpressViewModel(private val imageRepo: ImageRepository) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as PhotoExpressApplication)
            PhotoExpressViewModel(application.imageRepo)
         }
      }
   }

   private val _uiState = MutableStateFlow(PhotoExpressUiState())
   val uiState: StateFlow<PhotoExpressUiState> = _uiState

   suspend fun saveAlteredPhoto() {
      imageRepo.saveAlteredPhoto(uiState.value.colorFilter)
      _uiState.update {
         it.copy(photoSaved = true)
      }
   }

   fun photoTaken() {
      _uiState.update {
         it.copy(photoVisible = true)
      }
   }

   fun changeBrightness(brightness: Float) {
      _uiState.update {
         it.copy(
            colorFilter = imageRepo.changeBrightness(brightness),
            photoSaved = false
         )
      }
   }

   fun takePhoto(): Uri {
      changeBrightness(100f)   // Reset

      _uiState.update {
         it.copy(
            photoVisible = false,
            photoSaved = true,
            photoUri = imageRepo.createNewPhotoFile()
         )
      }

      return _uiState.value.photoUri
   }
}

data class PhotoExpressUiState(
   val photoUri: Uri = Uri.EMPTY,
   val colorFilter: LightingColorFilter = LightingColorFilter(Color.White, Color.Black),
   val photoVisible: Boolean = false,
   val photoSaved: Boolean = false
)