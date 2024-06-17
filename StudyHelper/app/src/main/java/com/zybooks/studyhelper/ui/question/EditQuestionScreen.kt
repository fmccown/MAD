package com.zybooks.studyhelper.ui.question

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionEditScreen(
   modifier: Modifier = Modifier,
   viewModel: EditQuestionViewModel = viewModel(
      factory = EditQuestionViewModel.Factory
   ),
   onUpClick: () -> Unit = {},
   onSaveClick: () -> Unit = {}
) {
   val question = viewModel.question

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Edit Question") },
            modifier = modifier,
            navigationIcon = {
               IconButton(onClick = onUpClick) {
                  Icon(Icons.Filled.ArrowBack,"Back")
               }
            }
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
         modifier = modifier.padding(innerPadding).fillMaxSize(),
      )
   }
}

