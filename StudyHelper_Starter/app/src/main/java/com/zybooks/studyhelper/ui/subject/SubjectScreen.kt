package com.zybooks.studyhelper.ui.subject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.theme.subjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
   modifier: Modifier = Modifier,
   viewModel: SubjectViewModel = viewModel(
      factory = SubjectViewModel.Factory
   ),
   onSubjectClick: (Subject) -> Unit = {},
) {
   val uiState = viewModel.uiState.collectAsStateWithLifecycle()

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Study Helper") }
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = { },
         ) {
            Icon(Icons.Filled.Add, "Add")
         }
      }
   ) { innerPadding ->
      SubjectGrid(
         subjectList = uiState.value.subjectList,
         onSubjectClick = onSubjectClick,
         modifier = modifier.padding(innerPadding),
      )
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectGrid(
   subjectList: List<Subject>,
   onSubjectClick: (Subject) -> Unit,
   modifier: Modifier = Modifier,
) {
   LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 128.dp),
      contentPadding = PaddingValues(0.dp),
      modifier = modifier
   ) {
      items(subjectList, key = { it.id }) { subject ->
         Card(
            colors = CardDefaults.cardColors(
               containerColor = subjectColors[
                  subject.title.length % subjectColors.size]
            ),
            onClick = { onSubjectClick(subject) },
            modifier = Modifier
               .animateItem()
               .height(100.dp)
               .padding(4.dp)
         ) {
            Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier.fillMaxSize()
            ) {
               Text(
                  text = subject.title,
                  textAlign = TextAlign.Center,
                  color = Color.White
               )
            }
         }
      }
   }
}
