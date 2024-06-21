package com.zybooks.petadoption.data

import com.zybooks.petadoption.R

class PetDataSource {
   private val petList = listOf(
      Pet(
         id = 1,
         type = PetType.DOG,
         name = "Trigger",
         gender = PetGender.MALE,
         age = 4,
         description = "Trigger is a smart dog who loves to bark.",
         imageId = R.drawable.pet1
      ),
      Pet(
         id = 2,
         type = PetType.DOG,
         name = "Gus",
         gender = PetGender.MALE,
         age = 4,
         description = "Gus is a fun-loving dog who loves to chase squirrels.",
         imageId = R.drawable.pet2
      ),
      Pet(
         id = 3,
         type = PetType.CAT,
         name = "Rufus",
         gender = PetGender.MALE,
         age = 2,
         description = "Rufus is a fun-loving cat with lots of energy.",
         imageId = R.drawable.pet3
      ),
      Pet(
         id = 4,
         type = PetType.DOG,
         name = "Daisy",
         gender = PetGender.FEMALE,
         age = 4,
         description = "Daisy is in search for her forever home.",
         imageId = R.drawable.pet4
      ),
      Pet(
         id = 5,
         type = PetType.DOG,
         name = "Gus",
         gender = PetGender.MALE,
         age = 5,
         description = "Gus is a fun-loving dog who loves to chase squirrels.",
         imageId = R.drawable.pet5
      ),
      Pet(
         id = 6,
         type = PetType.DOG,
         name = "Lilly",
         gender = PetGender.FEMALE,
         age = 5,
         description = "Lilly loves being outside.",
         imageId = R.drawable.pet6
      ),
      Pet(
         id = 7,
         type = PetType.DOG,
         name = "Bella",
         gender = PetGender.FEMALE,
         age = 4,
         description = "Bella is a sweetheart who loves long walks.",
         imageId = R.drawable.pet7
      ),
      Pet(
         id = 8,
         type = PetType.CAT,
         name = "Sam",
         gender = PetGender.MALE,
         age = 5,
         description = "Sam loves to sit and watch TV.",
         imageId = R.drawable.pet8
      ),
      Pet(
         id = 9,
         type = PetType.CAT,
         name = "Jessy",
         gender = PetGender.FEMALE,
         age = 2,
         description = "Jessy is a frisky cat with lots of energy.",
         imageId = R.drawable.pet9
      ),
      Pet(
         id = 10,
         type = PetType.CAT,
         name = "Bubba",
         gender = PetGender.MALE,
         age = 5,
         description = "Bubba is just looking for a good time.",
         imageId = R.drawable.pet10
      ),
      Pet(
         id = 11,
         type = PetType.OTHER,
         name = "Snowball",
         gender = PetGender.FEMALE,
         age = 1,
         description = "Snowball is a cutie with lots of love to give.",
         imageId = R.drawable.pet11
      ),
      Pet(
         id = 12,
         type = PetType.CAT,
         name = "Dina",
         gender = PetGender.FEMALE,
         age = 3,
         description = "Dina is a diva that craves lots of attention.",
         imageId = R.drawable.pet12
      ),
      Pet(
         id = 13,
         type = PetType.OTHER,
         name = "Jeff",
         gender = PetGender.MALE,
         age = 2,
         description = "Jeff is an energetic rabbit who is looking for a good home.",
         imageId = R.drawable.pet13
      ),
      Pet(
         id = 14,
         type = PetType.DOG,
         name = "Harley",
         gender = PetGender.MALE,
         age = 4,
         description = "Harley is a fun-loving dog who loves to chase squirrels.",
         imageId = R.drawable.pet14
      ),
      Pet(
         id = 15,
         type = PetType.CAT,
         name = "Freddie",
         gender = PetGender.MALE,
         age = 4,
         description = "Freddie is an adventurous cat who loves people.",
         imageId = R.drawable.pet15
      ),
      Pet(
         id = 16,
         type = PetType.DOG,
         name = "Benjie",
         gender = PetGender.MALE,
         age = 3,
         description = "Benjie is an adventurous dog who is looking for a home.",
         imageId = R.drawable.pet16
      ),
      Pet(
         id = 17,
         type = PetType.DOG,
         name = "Roger",
         gender = PetGender.MALE,
         age = 2,
         description = "Roger loves playing in the park.",
         imageId = R.drawable.pet17
      ),
      Pet(
         id = 18,
         type = PetType.DOG,
         name = "Betty",
         gender = PetGender.FEMALE,
         age = 4,
         description = "Betty is laid-back and loves being around people.",
         imageId = R.drawable.pet18
      ),
   )

   fun getPet(id: Int): Pet? {
      return petList.find { it.id == id }
   }

   fun loadPets() = petList
}