package com.zybooks.studyhelper.ui

import android.util.Log
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme
import com.zybooks.studyhelper.ui.theme.subjectColors

@Composable
fun SubjectScreen(
   subjectList: List<Subject>,
   onSubjectClick: (Subject) -> Unit,
   onAddSubject: (String) -> Unit,
   onDeleteSubjects: (List<Subject>) -> Unit,
   modifier: Modifier = Modifier
) {
   val selectedSubjects = rememberSaveable { mutableStateOf(emptySet<Subject>()) } // NEW
   val inSelectionMode by remember { derivedStateOf { selectedSubjects.value.isNotEmpty() } }
   var addSubjectDialogVisible by remember { mutableStateOf(false) }

   if (addSubjectDialogVisible) {
      AddSubjectDialog(
         onDismissRequest = {
            addSubjectDialogVisible = false
         },
         onConfirmation = {
            addSubjectDialogVisible = false
         },
         onAddSubject = onAddSubject
      )
   }

   Scaffold(
      topBar = {
         SubjectAppBar(
            inSelectionMode = inSelectionMode,
            onDeleteClick = {
               onDeleteSubjects(selectedSubjects.value.toList())
               selectedSubjects.value = emptySet()
            },
            onUpClick = {
               selectedSubjects.value = emptySet()
            }
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = { addSubjectDialogVisible = true },
         ) {
            Icon(Icons.Filled.Add, "Add subject")
         }
      }
   ) { innerPadding ->
      SubjectGrid(
         subjectList = subjectList,
         modifier = modifier.padding(innerPadding),
         inSelectionMode = inSelectionMode,
         selectedSubjects = selectedSubjects.value,
         onSubjectClick = onSubjectClick,
         onSubjectLongClick = { subject ->
            Log.d("McCown", "Long click on ${subject.title}")
            val selected = selectedSubjects.value.contains(subject)
            selectedSubjects.value = if (selected) {
               selectedSubjects.value.minus(subject)
            } else {
               selectedSubjects.value.plus(subject)
            }
         }
      )
   }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
   val subjectList = listOf(Subject(1, "Math"), Subject(2, "History"))
   StudyHelperTheme {
      SubjectScreen(subjectList, {}, {}, {})
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectGrid(
   subjectList: List<Subject>,
   modifier: Modifier = Modifier,
   onSubjectClick: (Subject) -> Unit,
   onSubjectLongClick: (Subject) -> Unit,
   inSelectionMode: Boolean = false,
   selectedSubjects: Set<Subject> = emptySet()
) {
   LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 128.dp),
      contentPadding = PaddingValues(0.dp),
      modifier = modifier
   ) {
      items(subjectList, key = { it.id }) { subject ->
         /*
         SubjectItem(
            subject = subject,
            selected = selectedSubjects.contains(subject),
            inSelectionMode = inSelectionMode,
            onClick = onSubjectClick,
            onLongClick = onSubjectLongClick
         )*/
         Card(
            colors =  CardDefaults.cardColors(
               containerColor = subjectColors[
                  subject.title.length % subjectColors.size]
            ),
            modifier = Modifier
               .animateItemPlacement()  // Requires key in items()!
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
            /*
            .clickable(
               onClick = { onClick(subject) },
               onClickLabel = subject.title
            )*/
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
                        modifier = Modifier.align(Alignment.TopStart)
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

/*
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubjectItem(
   subject: Subject,
   onClick: (Subject) -> Unit,
   onLongClick: (Subject) -> Unit,
   inSelectionMode: Boolean = false,
   selected: Boolean = false
) {
   Card(
      modifier = Modifier
         .height(100.dp)
         .padding(4.dp)
         .combinedClickable(
            onLongClick = { onLongClick(subject) },
            onClick = { onClick(subject) },
            onClickLabel = subject.title
         )
         /*
         .clickable(
            onClick = { onClick(subject) },
            onClickLabel = subject.title
         )*/
   ) {
      Box(
         contentAlignment = Alignment.Center,
         modifier = Modifier.fillMaxSize()
      ) {
         if (inSelectionMode) {
            if (selected) {
               Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  modifier = Modifier.align(Alignment.TopStart)
                     .padding(4.dp)
               )
            }
         }
         Text(
            text = subject.title,
            textAlign = TextAlign.Center
         )
      }
   }
}
*/

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

@Composable
fun AddSubjectDialog(
   onConfirmation: () -> Unit,
   onDismissRequest: () -> Unit,
   onAddSubject: (String) -> Unit
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
            onValueChange = { subject = it }
         )
      },
      confirmButton = {
         Button(
            colors = ButtonDefaults.buttonColors(
               containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
               onConfirmation()
               onAddSubject(subject)
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