package com.zybooks.petadoption.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PetApp(
   modifier: Modifier = Modifier,
   petViewModel: PetViewModel = viewModel()
) {
   Scaffold(
      topBar = {
         PetAppBar()
      }
   ) { innerPadding ->
      Text("Pet grid goes here", modifier.padding(innerPadding))
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAppBar(
   modifier: Modifier = Modifier
) {
   TopAppBar(
      title = { Text("Pet Adoption") },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer,
         titleContentColor = MaterialTheme.colorScheme.primary,
      ),
      modifier = modifier
   )
}
