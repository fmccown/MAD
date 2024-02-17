package com.zybooks.petadoption.ui

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.DataSource
import com.zybooks.petadoption.data.Pet

class PetViewModel : ViewModel() {

   val petList = DataSource().loadPets()

   //var selectedPetId = mutableIntStateOf(0)
   var selectedPet = mutableStateOf<Pet?>(null)

}