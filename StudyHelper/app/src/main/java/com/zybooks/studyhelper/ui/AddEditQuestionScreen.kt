package com.zybooks.studyhelper.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

@Composable
fun AddEditQuestionScreen(
   subject: Subject,
   modifier: Modifier = Modifier,
   question: Question = Question(0),
   onUpClick: () -> Unit = {},
   onSaveClick: (Question) -> Unit = {}
) {
   var questionInput by remember { mutableStateOf(question.text) }
   var answerInput by remember { mutableStateOf(question.answer) }
   val focusManager = LocalFocusManager.current

   Scaffold(
      topBar = {
         AddQuestionAppBar(
            title = if (question.id == 0L) "Add Question" else "Edit Question",
            onUpClick = onUpClick
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = {
               val q = Question(
                  id = question.id,
                  text = questionInput,
                  answer = answerInput,
                  subjectId = subject.id
               )
               onSaveClick(q)
            }
         ) {
            Icon(Icons.Filled.Done, "Save subject")
         }
      }
   ) { innerPadding ->
      Column(
         modifier = modifier.padding(innerPadding).fillMaxSize(),
         verticalArrangement = Arrangement.SpaceBetween
      ) {
         Row(modifier = modifier.weight(1f).fillMaxWidth()) {
            Text(
               text = "Q",
               fontSize = 80.sp,
               modifier = modifier.padding(8.dp)
            )
            TextField(
               value = questionInput,
               onValueChange = { questionInput = it },
               singleLine = false,
               keyboardOptions = KeyboardOptions(
                  imeAction = ImeAction.Next
               ),
               modifier = modifier.padding(8.dp)
            )
         }
         Row(modifier = modifier.weight(1f).fillMaxWidth()) {
            Text(
               text = "A",
               fontSize = 80.sp,
               modifier = modifier.padding(8.dp)
            )
            TextField(
               value = answerInput,
               onValueChange = { answerInput = it },
               label = { },
               singleLine = false,
               keyboardActions = KeyboardActions(
                  onDone = { focusManager.clearFocus() }
               ),
               keyboardOptions = KeyboardOptions(
                  imeAction = ImeAction.Done
               ),
               modifier = modifier.padding(8.dp)
            )
         }
      }
   }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAddQuestionScreen() {
   StudyHelperTheme {
      AddEditQuestionScreen(
         subject = Subject(0, "History")
      )
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionAppBar(
   title: String,
   modifier: Modifier = Modifier,
   onUpClick: () -> Unit = {}
) {
   TopAppBar(
      title = { Text(title) },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      modifier = modifier,
      navigationIcon = {
         IconButton(onClick = onUpClick) {
            Icon(Icons.Filled.ArrowBack,"Back")
         }
      }
   )
}

