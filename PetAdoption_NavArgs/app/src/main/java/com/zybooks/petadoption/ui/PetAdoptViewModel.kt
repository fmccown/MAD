package com.zybooks.petadoption.ui

import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.PetDataSource

class PetAdoptViewModel : ViewModel() {
   fun getPet(id: Int): Pet = PetDataSource().getPet(id) ?: Pet()
}