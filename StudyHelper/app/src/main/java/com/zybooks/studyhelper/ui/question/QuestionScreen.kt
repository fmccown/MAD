package com.zybooks.studyhelper.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuestionScreen(
   modifier: Modifier = Modifier,
   viewModel: QuestionViewModel = viewModel(
      factory = QuestionViewModel.Factory
   ),
   onUpClick: () -> Unit = {},
   onAddClick: () -> Unit = {},
   onEditClick: (Long) -> Unit = {},
) {
   val uiState = viewModel.uiState.collectAsStateWithLifecycle()
   var answerVisible by remember { mutableStateOf(true) }
   val showAnswerBtnLabel = if (answerVisible) "Hide Answer" else "Show Answer"

   Scaffold(
      topBar = {
         QuestionTopAppBar(
            subjectTitle = uiState.value.subject.title,
            questionNum = uiState.value.currQuestionNum,
            totalQuestions = uiState.value.totalQuestions,
            onUpClick = onUpClick
         )
      },
      bottomBar = {
         QuestionBottomBar(
            addOnly = uiState.value.totalQuestions == 0,
            onAddClick = onAddClick,
            onEditClick = { onEditClick(uiState.value.currQuestion.id) },
            onDeleteClick = viewModel::deleteQuestion
         )
      }
   ) { innerPadding ->
      if (uiState.value.totalQuestions > 0) {
         Column(
            modifier = modifier
               .padding(innerPadding)
               .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
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
               Text(
                  text = uiState.value.currQuestion.text,
                  fontSize = 30.sp,
                  lineHeight = 34.sp,
                  modifier = modifier.padding(8.dp)
               )
            }
            if (uiState.value.totalQuestions > 1) {
               Row(
                  modifier = modifier
                     .fillMaxWidth()
                     .padding(8.dp),
                  horizontalArrangement = Arrangement.SpaceBetween
               ) {
                  OutlinedIconButton(onClick = viewModel::prevQuestion) {
                     Icon(Icons.Filled.ArrowBack, "Previous")
                  }
                  Button(
                     onClick = { answerVisible = !answerVisible }
                  ) {
                     Text(showAnswerBtnLabel)
                  }
                  OutlinedIconButton(onClick = viewModel::nextQuestion) {
                     Icon(Icons.Filled.ArrowForward, "Next")
                  }
               }
            } else {
               Row(
                  modifier = modifier
                     .fillMaxWidth()
                     .padding(8.dp),
                  horizontalArrangement = Arrangement.Center
               ) {
                  Button(
                     onClick = { answerVisible = !answerVisible }
                  ) {
                     Text(showAnswerBtnLabel)
                  }
               }
            }
            Row(modifier = modifier
               .weight(1f)
               .fillMaxWidth()) {
               if (answerVisible) {
                  Text(
                     text = "A",
                     color = MaterialTheme.colorScheme.primary,
                     fontSize = 80.sp,
                     modifier = modifier.padding(8.dp)
                  )
                  Text(
                     text = uiState.value.currQuestion.answer,
                     fontSize = 30.sp,
                     lineHeight = 34.sp,
                     modifier = modifier.padding(8.dp)
                  )
               }
            }
         }
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppBar(
   subjectTitle: String,
   questionNum: Int,
   totalQuestions: Int,
   modifier: Modifier = Modifier,
   onUpClick: () -> Unit = {}
) {
   val title = if (totalQuestions == 0) "$subjectTitle (Empty)" else
      "$subjectTitle ($questionNum of $totalQuestions)"

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

@Composable
fun QuestionBottomBar(
   addOnly: Boolean = false,
   onAddClick: () -> Unit = {},
   onEditClick: () -> Unit = {},
   onDeleteClick: () -> Unit = {}
) {
   BottomAppBar(
      actions = {
         if (!addOnly) {
            IconButton(onClick = onEditClick) {
               Icon(
                  Icons.Filled.Edit,
                  contentDescription = "Edit",
               )
            }
            IconButton(onClick = onDeleteClick) {
               Icon(
                  Icons.Filled.Delete,
                  contentDescription = "Delete",
               )
            }
         }
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = onAddClick,
            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
         ) {
            Icon(Icons.Filled.Add, "Add")
         }
      }
   )
}