package com.zybooks.studyhelper.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

@Composable
fun SubjectScreen(
   subjectList: List<Subject>,
   onSubjectClick: (Subject) -> Unit,
   onAddSubject: (String) -> Unit,
   modifier: Modifier = Modifier
) {
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
         SubjectAppBar()
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
         onSubjectClick = onSubjectClick
      )
   }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
   val subjectList = listOf(Subject(1, "Math"), Subject(2, "History"))
   StudyHelperTheme {
      SubjectScreen(subjectList, {}, {})
   }
}

@Composable
fun SubjectGrid(
   subjectList: List<Subject>,
   modifier: Modifier = Modifier,
   onSubjectClick: (Subject) -> Unit
) {
   LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 128.dp),
      contentPadding = PaddingValues(0.dp),
      modifier = modifier
   ) {
      items(subjectList) { subject ->
         SubjectItem(
            subject = subject,
            onClick = onSubjectClick
         )
      }
   }
}

@Composable
fun SubjectItem(
   subject: Subject,
   onClick: (Subject) -> Unit,
   inSelectionMode: Boolean = false,
   selected: Boolean = false
) {
   Card(
      modifier = Modifier
         .height(100.dp)
         .padding(4.dp)
         .clickable(
            onClick = { onClick(subject) },
            onClickLabel = subject.title
         )
   ) {
      Box(
         contentAlignment = Alignment.Center,
         modifier = Modifier.fillMaxSize()
      ) {
         if (inSelectionMode) {
            if (selected) {
               Icon(Icons.Default.CheckCircle, null)
            }
         }
         Text(
            text = subject.title,
            textAlign = TextAlign.Center
         )
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectAppBar(
   modifier: Modifier = Modifier
) {
   TopAppBar(
      title = { Text("Study Helper") },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      modifier = modifier
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
               containerColor = MaterialTheme.colorScheme.error
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
               containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
               onDismissRequest()
            }) {
            Text(text = "Cancel")
         }
      },
   )
}