package com.zybooks.studyhelper.ui

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

@Composable
fun QuestionScreen(
   subject: Subject,
   questionList: List<Question>,
   currQuestionIndex: Int,
   modifier: Modifier = Modifier,
   onUpClick: () -> Unit = {},
   onLeftClick: () -> Unit = {},
   onRightClick: () -> Unit = {},
   onAddClick: () -> Unit = {},
   onEditClick: () -> Unit = {},
   onDeleteClick: (Question) -> Unit = {}
) {
   var answerVisible by remember { mutableStateOf(true) }
   val showAnswerBtnLabel = if (answerVisible) "Hide Answer" else "Show Answer"

   var questionNum = 0
   var question = Question()
   if (questionList.isNotEmpty()) {
      if (currQuestionIndex < questionList.size) {
         question = questionList[currQuestionIndex]
         questionNum = currQuestionIndex + 1
      } else {
         question = questionList.last()
         questionNum = questionList.size
      }
   }

   Scaffold(
      topBar = {
         QuestionTopAppBar(
            subjectTitle = subject.title,
            questionNum = questionNum,
            totalQuestions = questionList.size,
            onUpClick = onUpClick
         )
      },
      bottomBar = {
         QuestionBottomBar(
            showAddOnly = questionList.isEmpty(),
            onAddClick = onAddClick,
            onEditClick = onEditClick,
            onDeleteClick = { onDeleteClick(question) }
         )
      }
   ) { innerPadding ->
      if (questionList.isNotEmpty()) {
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
               Text(
                  text = question.text,
                  fontSize = 30.sp,
                  lineHeight = 34.sp,
                  modifier = modifier.padding(8.dp)
               )
            }
            Row(
               modifier = modifier.fillMaxWidth().padding(8.dp),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {

               OutlinedIconButton(onClick = onLeftClick) {
                  Icon(Icons.Filled.ArrowBack, "Previous")
               }
               Button(
                  onClick = { answerVisible = !answerVisible }
               ) {
                  Text(showAnswerBtnLabel)
               }
               OutlinedIconButton(onClick = onRightClick) {
                  Icon(Icons.Filled.ArrowForward, "Next")
               }
            }
            Row(modifier = modifier.weight(1f).fillMaxWidth()) {
               if (answerVisible) {
                  Text(
                     text = "A",
                     fontSize = 80.sp,
                     modifier = modifier.padding(8.dp)
                  )
                  Text(
                     text = question.answer,
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

@Preview(showSystemUi = true)
@Composable
fun PreviewQuestionScreen() {
   val subjectId = 0L
   val subject = Subject(subjectId, "History")
   val questionList = listOf(
      Question(
         text = "On what date was the U.S. Declaration of Independence adopted?",
         answer = "July 4, 1776",
         subjectId = subjectId
      ),
      Question(
         text = "When is tomorrow???",
         answer = "Oct 1, 2035",
         subjectId = subjectId
      )
   )
   StudyHelperTheme {
      QuestionScreen(
         subject = subject,
         questionList = questionList,
         currQuestionIndex = 0)
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

@Composable
fun QuestionBottomBar(
   modifier: Modifier = Modifier,
   showAddOnly: Boolean = false,
   onAddClick: () -> Unit = {},
   onEditClick: () -> Unit = {},
   onDeleteClick: () -> Unit = {}
) {
   BottomAppBar(
      actions = {
         if (!showAddOnly) {
            IconButton(onClick = onEditClick) {
               Icon(
                  Icons.Filled.Edit,
                  contentDescription = "Edit question",
               )
            }
            IconButton(onClick = onDeleteClick) {
               Icon(
                  Icons.Filled.Delete,
                  contentDescription = "Delete question",
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
            Icon(Icons.Filled.Add, "Add new question")
         }
      }
   )
}