package com.zybooks.petadoption.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.petadoption.PetAdoptionApp
import com.zybooks.petadoption.data.AppSettingsRepo
import com.zybooks.petadoption.data.DataSource
import com.zybooks.petadoption.data.MAX_AGE
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.PetType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
   private val appSettingsRepo: AppSettingsRepo
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PetAdoptionApp)
            SettingsViewModel(application.appSettingsRepo)
         }
      }
   }

   val uiState: StateFlow<SettingsUiState> =
      combine(
         appSettingsRepo.includeCats,
         appSettingsRepo.includeDogs,
         appSettingsRepo.includeOther,
         appSettingsRepo.maxAge
      ) { includeCats, includeDogs, includeOther, maxAge ->
         SettingsUiState(
            dogsChecked = includeDogs,
            catsChecked = includeCats,
            othersChecked = includeOther,
            maxAge = maxAge
         )
      }
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000),
         initialValue = SettingsUiState(
            dogsChecked = true,
            catsChecked = true,
            othersChecked = true,
            maxAge = MAX_AGE
         )
      )

   fun includeDogs(includeDogs: Boolean) {
      viewModelScope.launch {
         appSettingsRepo.saveIncludeDogs(includeDogs)
      }
   }

   fun includeCats(includeCats: Boolean) {
      viewModelScope.launch {
         appSettingsRepo.saveIncludeCats(includeCats)
      }
   }

   fun includeOthers(includeOthers: Boolean) {
      viewModelScope.launch {
         appSettingsRepo.saveIncludeOthers(includeOthers)
      }
   }

   fun setMaxAge(maxAge: Int) {
      viewModelScope.launch {
         appSettingsRepo.saveMaxAge(maxAge)
      }
   }
}

data class SettingsUiState (
   val dogsChecked: Boolean,
   val catsChecked: Boolean,
   val othersChecked: Boolean,
   val maxAge: Int
)