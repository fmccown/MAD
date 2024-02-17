package com.zybooks.petadoption.data

enum class PetType {
   DOG, CAT, OTHER
}

enum class PetGender {
   MALE, FEMALE
}

data class Pet (
   val id: Int,
   val type: PetType,
   val name: String,
   val gender: PetGender,
   val age: Int,
   val description: String,
   val imageId: Int = 0
)