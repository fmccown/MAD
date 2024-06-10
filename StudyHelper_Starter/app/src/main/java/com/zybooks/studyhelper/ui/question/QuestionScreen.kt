package com.zybooks.studyhelper.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.studyhelper.data.Question

@Composable
fun QuestionScreen(
   viewModel: QuestionViewModel = viewModel(
      factory = QuestionViewModel.Factory
   ),
   onUpClick: () -> Unit,
   onAddClick: () -> Unit,
   onEditClick: (Long) -> Unit,
) {
   val uiState = viewModel.uiState.collectAsStateWithLifecycle()

   Scaffold(
      topBar = {
         QuestionTopBar(
            subjectTitle = uiState.value.subject.title,
            questionNum = uiState.value.currQuestionNum,
            totalQuestions = uiState.value.totalQuestions,
            onUpClick = onUpClick
         )
      },
      bottomBar = {
         QuestionBottomBar(
            showAddOnly = uiState.value.totalQuestions == 0,
            onAddClick = onAddClick,
            onEditClick = { onEditClick(uiState.value.currQuestion.id) },
            onDeleteClick = {  }
         )
      }
   ) { innerPadding ->
      if (uiState.value.totalQuestions > 0) {
         QuestionAndAnswer(
            question = uiState.value.currQuestion,
            totalQuestions = uiState.value.totalQuestions,
            onPrevClick = viewModel::prevQuestion,
            onNextClick = viewModel::nextQuestion,
            onToggleAnswerClick = viewModel::toggleAnswer,
            answerVisible = uiState.value.answerVisible,
            modifier = Modifier
               .padding(innerPadding)
               .fillMaxSize()
         )
      }
   }
}

@Composable
fun QuestionAndAnswer(
   question: Question,
   totalQuestions: Int,
   onPrevClick: () -> Unit,
   onNextClick: () -> Unit,
   onToggleAnswerClick: () -> Unit,
   answerVisible: Boolean,
   modifier: Modifier = Modifier,
) {
   val showAnswerBtnLabel = if (answerVisible) "Hide Answer" else "Show Answer"

   Column(
      modifier = modifier,
      verticalArrangement = Arrangement.SpaceBetween
   ) {
      Row(
         modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
      ) {
         Text(
            text = "Q",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 80.sp,
            modifier = Modifier.padding(8.dp)
         )
         Text(
            text = question.text,
            fontSize = 30.sp,
            lineHeight = 34.sp,
            modifier = Modifier.padding(8.dp)
         )
      }
      Row(
         modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         if (totalQuestions > 1) {
            OutlinedIconButton(onClick = onPrevClick) {
               Icon(Icons.Filled.ArrowBack, "Previous")
            }
         } else {
            Spacer(Modifier)
         }
         Button(
            onClick = onToggleAnswerClick
         ) {
            Text(showAnswerBtnLabel)
         }
         if (totalQuestions > 1) {
            OutlinedIconButton(onClick = onNextClick) {
               Icon(Icons.Filled.ArrowForward, "Next")
            }
         } else {
            Spacer(Modifier)
         }
      }
      Row(modifier = Modifier
         .weight(1f)
         .fillMaxWidth()) {
         if (answerVisible) {
            Text(
               text = "A",
               color = MaterialTheme.colorScheme.primary,
               fontSize = 80.sp,
               modifier = Modifier.padding(8.dp)
            )
            Text(
               text = question.answer,
               fontSize = 30.sp,
               lineHeight = 34.sp,
               modifier = Modifier.padding(8.dp)
            )
         }
      }
   }
}

@Preview
@Composable
fun PreviewQuestionAndAnswer() {
   QuestionAndAnswer(
      question = Question(0, "What is 2 + 2?\nline2\nline3\nline4\nline5\nline6", "The answer is 4."),
      totalQuestions = 2,
      onPrevClick = {},
      onNextClick = {},
      onToggleAnswerClick = {},
      answerVisible = true
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopBar(
   subjectTitle: String,
   questionNum: Int,
   totalQuestions: Int,
   onUpClick: () -> Unit,
   modifier: Modifier = Modifier
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
   showAddOnly: Boolean,
   onAddClick: () -> Unit,
   onEditClick: () -> Unit,
   onDeleteClick: () -> Unit
) {
   BottomAppBar(
      actions = {
         if (!showAddOnly) {
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