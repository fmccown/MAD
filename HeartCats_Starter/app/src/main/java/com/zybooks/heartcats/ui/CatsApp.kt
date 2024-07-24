package com.zybooks.heartcats.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsApp(
   viewModel: CatsViewModel = viewModel(
      factory = CatsViewModel.Factory
   )
) {
   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Heart Cats") },
            actions = {
               IconButton(onClick = { viewModel.getCatImages() }) {
                  Icon(
                     Icons.Filled.Refresh,
                     contentDescription = "More Cats",
                  )
               }
            }
         )
      }
   ) { innerPadding ->
      Box(
         Modifier.padding(innerPadding),
      ) {
         when (val uiState = viewModel.catsUiState) {
            is CatsUiState.Loading -> Text("Loading...")
            is CatsUiState.Success -> Text(uiState.catImages.toString())
            is CatsUiState.Error -> Text("Error: ${uiState.errorMessage}")
         }
      }
   }
}
