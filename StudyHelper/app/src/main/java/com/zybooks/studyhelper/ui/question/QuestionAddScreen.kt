package com.zybooks.studyhelper.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

@Composable
fun QuestionAddScreen(
   modifier: Modifier = Modifier,
   viewModel: QuestionAddViewModel = viewModel(
      factory = QuestionAddViewModel.Factory
   ),
   onUpClick: () -> Unit = {},
   onSaveClick: () -> Unit = {}
) {
   Scaffold(
      topBar = {
         AddQuestionAppBar(
            title = "Add Question",
            onUpClick = onUpClick
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = {
               viewModel.addQuestion()
               onSaveClick()
            }
         ) {
            Icon(Icons.Filled.Done, "Save")
         }
      }
   ) { innerPadding ->
      QuestionEntry(
         question = viewModel.question,
         onQuestionChange = { viewModel.changeQuestion(it) },
         innerPadding = innerPadding,
         modifier = modifier
         //   .padding(innerPadding)
         //   .fillMaxSize(),
      )
   }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAddQuestionScreen() {
   StudyHelperTheme {
      QuestionAddScreen(
         //subject = Subject(0, "History")
      )
   }
}

@Composable
fun QuestionEntry(
   question: Question,
   onQuestionChange: (Question) -> Unit,
   modifier: Modifier = Modifier,
   innerPadding: PaddingValues
) {
   val focusManager = LocalFocusManager.current

   Column(
      modifier = modifier
         .padding(innerPadding)
         .fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween,
   ) {
      Row(modifier = modifier
         .weight(1f)
         .fillMaxWidth()) {
         Text(
            text = "Q",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 80.sp,
            modifier = modifier.padding(8.dp)
         )
         TextField(
            value = question.text,
            onValueChange = { onQuestionChange(question.copy(text = it)) },
            singleLine = false,
            keyboardOptions = KeyboardOptions(
               imeAction = ImeAction.Next
            ),
            modifier = modifier.padding(8.dp)
         )
      }
      Row(modifier = modifier
         .weight(1f)
         .fillMaxWidth()) {
         Text(
            text = "A",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 80.sp,
            modifier = modifier.padding(8.dp)
         )
         TextField(
            value = question.answer,
            onValueChange = { onQuestionChange(question.copy(answer = it)) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionAppBar(
   title: String,
   modifier: Modifier = Modifier,
   onUpClick: () -> Unit = {}
) {
   TopAppBar(
      title = { Text(title) },
      modifier = modifier,
      navigationIcon = {
         IconButton(onClick = onUpClick) {
            Icon(Icons.Filled.ArrowBack,"Back")
         }
      }
   )
}

