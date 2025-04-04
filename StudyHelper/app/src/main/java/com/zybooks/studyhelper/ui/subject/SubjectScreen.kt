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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
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
   onSubjectClick: (Subject) -> Unit = {}
) {
   val uiState = viewModel.uiState.collectAsStateWithLifecycle()

   if (uiState.value.isSubjectDialogVisible) {
      AddSubjectDialog(
         onConfirmation = { title ->
            viewModel.hideSubjectDialog()
            viewModel.addSubject(title)
         },
         onDismissRequest = {
            viewModel.hideSubjectDialog()
         }
      )
   }

   Scaffold(
      topBar = {
         if (uiState.value.isCabVisible) {
            CabAppBar(
               onDeleteClick = { viewModel.deleteSelectedSubjects() },
               onUpClick = { viewModel.hideCab() }
            )
         } else {
            TopAppBar(
               title = { Text("Study Helper") }
            )
         }
      },
      floatingActionButton = {
         if (!uiState.value.isCabVisible) {
            FloatingActionButton(
               onClick = { viewModel.showSubjectDialog() },
            ) {
               Icon(Icons.Filled.Add, "Add")
            }
         }
      }
   ) { innerPadding ->
      SubjectGrid(
         subjectList = uiState.value.subjectList,
         inSelectionMode = uiState.value.isCabVisible,
         selectedSubjects = uiState.value.selectedSubjects,
         onSubjectClick = onSubjectClick,
         onSelectSubject = { viewModel.selectSubject(it) },
         modifier = modifier.padding(innerPadding)
      )
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectGrid(
   subjectList: List<Subject>,
   onSubjectClick: (Subject) -> Unit,
   modifier: Modifier = Modifier,
   onSelectSubject: (Subject) -> Unit = { },
   inSelectionMode: Boolean = false,
   selectedSubjects: Set<Subject> = emptySet()
) {
   val haptics = LocalHapticFeedback.current

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
               .animateItem()
               .height(100.dp)
               .padding(4.dp)
               .combinedClickable(
                  onLongClick = {
                     haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                     onSelectSubject(subject)
                  },
                  onClick = {
                     if (inSelectionMode) {
                        onSelectSubject(subject)
                     } else {
                        onSubjectClick(subject)
                     }
                  },
                  onClickLabel = subject.title,
                  onLongClickLabel = "Select for deleting"
               )
         ) {
            Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier.fillMaxSize()
            ) {
               if (selectedSubjects.contains(subject)) {
                  Icon(
                     imageVector = Icons.Default.CheckCircle,
                     contentDescription = "Check",
                     tint = Color.White,
                     modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                  )
               }
               Text(
                  text = subject.title,
                  color = Color.White
               )
            }
         }
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CabAppBar(
   onDeleteClick: () -> Unit,
   onUpClick: () -> Unit,
   modifier: Modifier = Modifier
) {
   TopAppBar(
      title = { },
      modifier = modifier,
      navigationIcon = {
         IconButton(onClick = onUpClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
         }
      },
      actions = {
         IconButton(onClick = onDeleteClick) {
            Icon(Icons.Filled.Delete, "Delete")
         }
      }
   )
}

@Composable
fun AddSubjectDialog(
   onConfirmation: (String) -> Unit,
   onDismissRequest: () -> Unit,
) {
   var subject by remember { mutableStateOf("") }

   AlertDialog(
      onDismissRequest = {
         onDismissRequest()
      },
      title = {
         TextField(
            label = { Text("Subject?") },
            value = subject,
            onValueChange = { subject = it },
            singleLine = true,
            keyboardActions = KeyboardActions(
               onDone = {
                  onConfirmation(subject)
               }
            )
         )
      },
      confirmButton = {
         Button(
            colors = ButtonDefaults.buttonColors(
               containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
               onConfirmation(subject)
            }) {
            Text(text = "Add")
         }
      },
      dismissButton = {
         Button(
            colors = ButtonDefaults.buttonColors(
               containerColor = MaterialTheme.colorScheme.secondary
            ),
            onClick = {
               onDismissRequest()
            }) {
            Text(text = "Cancel")
         }
      },
   )
}