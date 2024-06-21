package com.zybooks.petadoption.ui

import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.PetDataSource

class PetListViewModel : ViewModel() {
   val petList = PetDataSource().loadPets()
}