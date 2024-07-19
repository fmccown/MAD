package com.zybooks.heartcats.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zybooks.heartcats.R
import com.zybooks.heartcats.data.Cat

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
               IconButton(onClick = { viewModel.getCats() }) {
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
            is CatsUiState.Loading -> LoadingScreen()
            is CatsUiState.Success -> CatScreen(uiState.cats)
            is CatsUiState.Error -> ErrorScreen(uiState.errorMessage)
         }
      }
   }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingScreen() {
   Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxSize()
   ) {
      CircularProgressIndicator(
         modifier = Modifier
            .padding(bottom = 20.dp)
            .size(60.dp)
      )
      Text(
         text = "Loading...",
         fontSize = 30.sp,
      )
   }
}

@Composable
fun CatScreen(cats: List<Cat>) {
   LazyVerticalStaggeredGrid(
      columns = StaggeredGridCells.Fixed(2),
      verticalItemSpacing = 4.dp,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
   ) {
      items(items = cats, key = { cat -> cat.id }) { cat ->
         AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
               .data(cat.url)
               .crossfade(true)
               .build(),
            error = painterResource(R.drawable.baseline_broken_image_24),
            //placeholder = painterResource(R.drawable.baseline_downloading_24),
            contentDescription = "Cat image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
               .fillMaxWidth()
               .wrapContentHeight()
         )
      }
   }
}

@Composable
fun ErrorScreen(errorMessage: String) {
   Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(16.dp)
   ) {
      Icon(
         painter = painterResource(R.drawable.baseline_error_24),
         contentDescription = errorMessage,
         modifier = Modifier
            .padding(bottom = 20.dp)
            .size(148.dp),
         tint = MaterialTheme.colorScheme.error
      )
      Text(text = errorMessage)
   }
}