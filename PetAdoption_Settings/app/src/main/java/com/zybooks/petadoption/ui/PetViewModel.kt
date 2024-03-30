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
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.PetType
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PetViewModel(
   private val appSettingsRepo: AppSettingsRepo
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PetAdoptionApp)
            PetViewModel(application.appSettingsRepo)
         }
      }
   }

   //val petList = DataSource().loadPets()
   private val _petList = MutableLiveData<List<Pet>>()
   val petList: LiveData<List<Pet>> get() = _petList

   // Only downside is that it is exposed as mutable, so could be changed outside
   //val petList = MutableLiveData<List<Pet>>()

   // This is to use LiveData instead of Flows
   init {
      viewModelScope.launch {
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
         }.collect { pets ->
            println("Return pets with length ${pets.size}")
            _petList.value = pets
            //petList.value = pets
         }
      }
   }

   /*
   val uiState: StateFlow<UiState> =
      combine(
         appSettingsRepo.includeCats,
         appSettingsRepo.includeDogs,
         appSettingsRepo.includeOther,
         appSettingsRepo.maxAge
      ) { includeCats, includeDogs, includeOther, maxAge ->
         UiState(
            DataSource().loadPets().filter { pet ->
               ((includeCats && pet.type == PetType.CAT) ||
               (includeDogs && pet.type == PetType.DOG) ||
               (includeOther && pet.type == PetType.OTHER)) &&
                  pet.age <= maxAge
            }
         )
      }
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000),
         initialValue = UiState(emptyList())
      )
    */

   /*
      appSettingsRepo.includeCats.map { includeCats ->
         UiState(DataSource().loadPets().filter { includeCats && it.type == PetType.CAT })
      }
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000),
         initialValue = UiState(DataSource().loadPets())
      )*/

   var selectedPet by mutableStateOf<Pet?>(null)
}

data class UiState (
   val petList: List<Pet>
)