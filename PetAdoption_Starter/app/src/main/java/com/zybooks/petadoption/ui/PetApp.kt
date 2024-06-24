package com.zybooks.petadoption.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
   Text("To be implemented...")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAppBar(
   title: String,
   modifier: Modifier = Modifier
) {
   TopAppBar(
      title = { Text(title) },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      modifier = modifier
   )
}
