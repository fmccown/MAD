package com.zybooks.heartcats.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.heartcats.CatsApplication
import com.zybooks.heartcats.data.Cat
import com.zybooks.heartcats.data.CatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface CatsUiState {
   data class Success(val cats: List<Cat>) : CatsUiState
   data class Error(val errorMessage: String) : CatsUiState
   data object Loading : CatsUiState
}

class CatsViewModel(private val catsRepository: CatRepository) : ViewModel() {
   var catsUiState: CatsUiState by mutableStateOf(CatsUiState.Loading)
      private set

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as CatsApplication)

            CatsViewModel(
               catsRepository = application.catsRepository
            )
         }
      }
   }

   init {
      getCats()
   }

   fun getCats() {
      viewModelScope.launch {
         catsUiState = CatsUiState.Loading
         catsUiState = try {
            delay(1000)
            CatsUiState.Success(catsRepository.getCats())
         } catch (e: Exception) {
            CatsUiState.Error(e.message.toString())
         }
      }
   }
}