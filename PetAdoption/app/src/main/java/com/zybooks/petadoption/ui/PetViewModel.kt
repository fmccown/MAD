package com.zybooks.petadoption.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.PetDataSource
import com.zybooks.petadoption.data.Pet

class PetViewModel : ViewModel() {
   val petList = PetDataSource().loadPets()
   var selectedPet by mutableStateOf(Pet())
}