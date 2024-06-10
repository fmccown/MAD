package com.zybooks.studyhelper.ui.subject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun SubjectScreen(
   modifier: Modifier = Modifier,
   viewModel: SubjectViewModel = viewModel(
      factory = SubjectViewModel.Factory
   ),
   onSubjectClick: (Subject) -> Unit = {}
) {
   val uiState = viewModel.uiState.collectAsStateWithLifecycle()

   Scaffold(
      topBar = {
         SubjectAppBar(
            inSelectionMode = uiState.value.inSelectionMode,
            onDeleteClick = { },
            onUpClick = { }
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
         inSelectionMode = uiState.value.inSelectionMode,
         selectedSubjects = uiState.value.selectedSubjects,
         onSubjectClick = onSubjectClick,
         onSubjectLongClick = { },
         modifier = modifier.padding(innerPadding)
      )
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectGrid(
   subjectList: List<Subject>,
   onSubjectClick: (Subject) -> Unit,
   onSubjectLongClick: (Subject) -> Unit,
   modifier: Modifier = Modifier,
   inSelectionMode: Boolean = false,
   selectedSubjects: Set<Subject> = emptySet()
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
            modifier = Modifier
               .animateItemPlacement()  // Requires key in items()
               .height(100.dp)
               .padding(4.dp)
               .combinedClickable(
                  onLongClick = { onSubjectLongClick(subject) },
                  onClick = {
                     if (inSelectionMode) {
                        onSubjectLongClick(subject)
                     } else {
                        onSubjectClick(subject)
                     }
                  },
                  onClickLabel = subject.title
               )
         ) {
            Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier.fillMaxSize()
            ) {
               if (inSelectionMode) {
                  if (selectedSubjects.contains(subject)) {
                     Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                           .align(Alignment.TopStart)
                           .padding(4.dp)
                     )
                  }
               }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectAppBar(
   inSelectionMode: Boolean,
   onDeleteClick: () -> Unit,
   onUpClick: () -> Unit,
   modifier: Modifier = Modifier
) {
   TopAppBar(
      title = { Text("Study Helper") },
      modifier = modifier,
      navigationIcon = {
         if (inSelectionMode) {
            IconButton(onClick = onUpClick) {
               Icon(Icons.Filled.ArrowBack,"Back")
            }
         }
      },
      actions = {
         if (inSelectionMode) {
            IconButton(onClick = onDeleteClick) {
               Icon(Icons.Filled.Delete, "Delete")
            }
         }
      }
   )
}