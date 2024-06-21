package com.zybooks.petadoption.data

enum class PetType {
   DOG, CAT, OTHER
}

enum class PetGender {
   MALE, FEMALE
}

data class Pet (
   val id: Int = 0,
   val type: PetType = PetType.DOG,
   val name: String = "",
   val gender: PetGender = PetGender.FEMALE,
   val age: Int = 0,
   val description: String = "",
   val imageId: Int = 0
)