package com.zybooks.petadoption.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zybooks.petadoption.data.DataSource
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.PetGender
import com.zybooks.petadoption.ui.theme.PetAdoptionTheme

enum class PetScreen(val title: String) {
   LIST("Find a Friend"),
   DETAIL("Details"),
   ADOPT("Thank You!")
}

@Composable
fun PetApp(
   modifier: Modifier = Modifier,
   petViewModel: PetViewModel = viewModel()
) {
   val navController = rememberNavController()
   val backStackEntry by navController.currentBackStackEntryAsState()
   val currentScreen = PetScreen.valueOf(
      backStackEntry?.destination?.route ?: PetScreen.LIST.name
   )

   Scaffold(
      topBar = {
         PetAppBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            onUpClick = { navController.navigateUp() }
         )
      }
   ) { innerPadding ->
      NavHost(
         navController = navController,
         startDestination = PetScreen.LIST.name,
         modifier = modifier.padding(innerPadding)
      ) {
         composable(route = PetScreen.LIST.name) {
            ListScreen(
               petList = petViewModel.petList,
               onImageClick = {
                  petViewModel.selectedPet = it
                  navController.navigate(PetScreen.DETAIL.name)
               }
            )
         }

         composable(route = PetScreen.DETAIL.name) {
            DetailScreen(
               pet = petViewModel.selectedPet!!,
               onAdoptClick = {
                  navController.navigate(PetScreen.ADOPT.name)
               }
            )
         }

         composable(route = PetScreen.ADOPT.name) {
            AdoptScreen(
               pet = petViewModel.selectedPet!!
            )
         }
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAppBar(
   currentScreen: PetScreen,
   canNavigateBack: Boolean,
   onUpClick: () -> Unit,
   modifier: Modifier = Modifier
) {
   TopAppBar(
      title = { Text(currentScreen.title) },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      modifier = modifier,
      navigationIcon = {
         if (canNavigateBack) {
            IconButton(onClick = onUpClick) {
               Icon(Icons.Filled.ArrowBack,"Back")
            }
         }
      }
   )
}

@Composable
fun ListScreen(
   petList: List<Pet>,
   onImageClick: (Pet) -> Unit
) {
   LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 128.dp),
      contentPadding = PaddingValues(0.dp)
   ) {
      items(petList) { pet ->
         Image(
            painter = painterResource(id = pet.imageId),
            contentDescription = "${pet.type} ${pet.gender}",
            modifier = Modifier.clickable(
               onClick = { onImageClick(pet) },
               onClickLabel = "Select the pet"
            )
         )
      }
   }
}

@Composable
fun DetailScreen(
   pet: Pet,
   onAdoptClick: () -> Unit,
   modifier: Modifier = Modifier
) {
   val gender = if (pet.gender == PetGender.MALE) "Male" else "Female"

   Column {
      Image(
         painter = painterResource(pet.imageId),
         contentDescription = pet.name,
         contentScale = ContentScale.FillWidth
      )
      Column(
         verticalArrangement = Arrangement.spacedBy(6.dp),
         modifier = modifier.padding(6.dp)
      ) {
         Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
         ) {
            Text(
               text = pet.name,
               style = MaterialTheme.typography.headlineMedium
            )
            Button(onClick = onAdoptClick) {
               Text("Adopt Me!")
            }
         }
         Text(
            text = "Gender: $gender",
            style = MaterialTheme.typography.bodyLarge
         )
         Text(
            text = "Age: ${pet.age}",
            style = MaterialTheme.typography.bodyLarge
         )
         Text(
            text = pet.description,
            style = MaterialTheme.typography.bodyMedium
         )
      }
   }
}

@Composable
fun AdoptScreen(
   pet: Pet,
   modifier: Modifier = Modifier
) {
   val context = LocalContext.current
   Column {
      Row(verticalAlignment = Alignment.CenterVertically) {
         Image(
            painter = painterResource(pet.imageId),
            contentDescription = pet.name,
            modifier = modifier.size(150.dp)
         )
         Text(
            text = "Thank you for adopting ${pet.name}!",
            modifier = modifier.padding(horizontal = 28.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
         )
      }
      Text(
         text = "Please pick up your new family member during business hours.",
         modifier = modifier.padding(6.dp),
      )
      Button(
         onClick = { shareAdoption(context, pet) },
         modifier = modifier.padding(6.dp)
      ) {
         Icon(Icons.Default.Share, null)
         Text("Share", modifier = modifier.padding(start = 8.dp))
      }
   }
}

private fun shareAdoption(context: Context, pet: Pet) {
   val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, "Meet ${pet.name}!")
      putExtra(Intent.EXTRA_TEXT, "I've adopted ${pet.name}!")
   }

   context.startActivity(
      Intent.createChooser(intent,"Pet Adoption")
   )
}

@Preview
@Composable
fun PreviewAdoptScreen() {
   val pet = DataSource().loadPets()[0]
   PetAdoptionTheme {
      AdoptScreen(pet)
   }
}

@Preview(showBackground = true)
@Composable
fun PreviewGridScreen() {
   PetAdoptionTheme {
      PetApp()
   }
}

@Preview
@Composable
fun PreviewDetailScreen() {
   val pet = DataSource().loadPets()[0]
   PetAdoptionTheme {
      DetailScreen(pet, onAdoptClick = {})
   }
}
