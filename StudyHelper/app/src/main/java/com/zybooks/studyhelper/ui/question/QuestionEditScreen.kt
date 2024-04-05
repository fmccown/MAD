package com.zybooks.studyhelper.ui.question

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

@Composable
fun QuestionEditScreen(
   modifier: Modifier = Modifier,
   viewModel: QuestionEditViewModel = viewModel(
      factory = QuestionEditViewModel.Factory
   ),
   onUpClick: () -> Unit = {},
   onSaveClick: () -> Unit = {}
) {
   val question = viewModel.question

   Scaffold(
      topBar = {
         EditQuestionAppBar(
            title = "Edit Question",
            onUpClick = onUpClick
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = {
               viewModel.updateQuestion()
               onSaveClick()
            }
         ) {
            Icon(Icons.Filled.Done, "Save")
         }
      }
   ) { innerPadding ->
      QuestionEntry(
         question = question,
         onQuestionChange = { viewModel.changeQuestion(it) },
         innerPadding = innerPadding
         //modifier = modifier
         //   .padding(innerPadding)
         //   .fillMaxSize(),
      )
   }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewQuestionEditScreen() {
   val q = Question(0, "What is 2 + 2?", "4")
   StudyHelperTheme {
      QuestionEditScreen(
         //questionId = 0L
         //subject = Subject(0, "History")
      )
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuestionAppBar(
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

