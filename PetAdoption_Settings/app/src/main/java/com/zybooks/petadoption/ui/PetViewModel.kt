package com.zybooks.petadoption.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.petadoption.PetAdoptionApplication
import com.zybooks.petadoption.data.AppSettingsRepo
import com.zybooks.petadoption.data.DataSource
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.PetType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class PetViewModel(
   appSettingsRepo: AppSettingsRepo
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PetAdoptionApplication)
            PetViewModel(application.appSettingsRepo)
         }
      }
   }

   // Apply the settings to the pet list
   val petList: StateFlow<List<Pet>> =
      combine(
         appSettingsRepo.includeCats,
         appSettingsRepo.includeDogs,
         appSettingsRepo.includeOther,
         appSettingsRepo.maxAge
      ) { includeCats, includeDogs, includeOther, maxAge ->
         DataSource().loadPets().filter { pet ->
            ((includeCats && pet.type == PetType.CAT) ||
                  (includeDogs && pet.type == PetType.DOG) ||
                  (includeOther && pet.type == PetType.OTHER)) &&
                  pet.age <= maxAge
         }
      }.stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000),
         initialValue = emptyList()
      )

   var selectedPet by mutableStateOf<Pet?>(null)
}
