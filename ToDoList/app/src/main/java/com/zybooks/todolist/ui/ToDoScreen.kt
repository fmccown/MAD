package com.zybooks.todolist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.todolist.R
import com.zybooks.todolist.Task
import com.zybooks.todolist.ui.theme.ToDoListTheme

@Composable
fun ToDoScreen(
   modifier: Modifier = Modifier,
   todoViewModel: ToDoViewModel = viewModel()
) {
   Scaffold(
      topBar = {
         ToDoAppTopBar(
            completedTasksExist = todoViewModel.completedTasksExist,
            onDeleteCompletedTasks = todoViewModel::deleteCompletedTasks,
            onCreateTasks = todoViewModel::createTasks,
            archivedTasksExist = todoViewModel.archivedTasksExist,
            onRestoreArchive = todoViewModel::restoreArchivedTasks
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
      ) {
         AddTaskInput { todoViewModel.addTask(it) }
         TaskList(
            taskList = todoViewModel.taskList,
            onDeleteTask = todoViewModel::deleteTask,
            onArchiveTask = todoViewModel::archiveTask,
            onToggleTaskComplete = todoViewModel::toggleTaskCompleted
         )
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskList(
   taskList: List<Task>,
   onDeleteTask: (Task) -> Unit,
   onArchiveTask: (Task) -> Unit,
   onToggleTaskComplete: (Task) -> Unit
) {
   LazyColumn {
      items(
         items = taskList,
         key = { task -> task.id }
      ) { task ->
         val currentTask by rememberUpdatedState(task)
         val dismissState = rememberDismissState(
            confirmValueChange = {
               when (it) {
                  DismissValue.DismissedToEnd -> {
                     onDeleteTask(currentTask)
                     true
                  }

                  DismissValue.DismissedToStart -> {
                     onArchiveTask(currentTask)
                     true
                  }

                  else -> false
               }
            }
         )

         SwipeToDismiss(
            state = dismissState,
            background = { SwipeBackground(dismissState) },
            modifier = Modifier
               .padding(vertical = 1.dp)
               .animateItemPlacement(),
            dismissContent = {
               TaskCard(
                  task = task,
                  toggleCompleted = onToggleTaskComplete
               )
            }
         )
      }
   }
}

@Composable
fun TaskCard(
   task: Task,
   toggleCompleted: (Task) -> Unit,
   modifier: Modifier = Modifier
) {
   Card(
      modifier = modifier
         .padding(8.dp)
         .fillMaxWidth(),
      colors = CardDefaults.cardColors(
         containerColor = MaterialTheme.colorScheme.surfaceVariant
      )
   ) {
      Row(
         modifier = modifier.fillMaxWidth(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         Text(
            text = task.body,
            modifier = modifier.padding(start = 12.dp),
            color = if (task.completed) Color.Gray else Color.Black
         )
         Checkbox(
            checked = task.completed,
            onCheckedChange = {
               toggleCompleted(task)
            }
         )
      }
   }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SwipeBackground(
   dismissState: DismissState,
   modifier: Modifier = Modifier
) {
   val color = when (dismissState.dismissDirection) {
      DismissDirection.EndToStart -> Color.Green
      DismissDirection.StartToEnd -> Color.Red
      null -> Color.Transparent
   }

   Row(
      modifier
         .fillMaxSize()
         .background(color)
         .padding(horizontal = 15.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
   ) {
      if (dismissState.dismissDirection == DismissDirection.StartToEnd) {
         Icon(
            Icons.Default.Delete,
            contentDescription = "Delete",
         )
      }
      Spacer(modifier = Modifier)
      if (dismissState.dismissDirection == DismissDirection.EndToStart) {
         Icon(
            painter = painterResource(R.drawable.archive),
            contentDescription = "Archive",
         )
      }
   }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskInput(onEnterTask: (String) -> Unit) {
   val keyboardController = LocalSoftwareKeyboardController.current
   var taskBody by remember { mutableStateOf("") }

   OutlinedTextField(
      modifier = Modifier
         .fillMaxWidth()
         .padding(6.dp),
      value = taskBody,
      onValueChange = { taskBody = it },
      label = { Text("Enter task") },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(
         onDone = {
            onEnterTask(taskBody)
            taskBody = ""
            keyboardController?.hide()
         }),
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppTopBar(
   completedTasksExist: Boolean,
   onDeleteCompletedTasks: () -> Unit,
   archivedTasksExist: Boolean,
   onRestoreArchive: () -> Unit,
   onCreateTasks: () -> Unit
) {
   var showDeleteTasksDialog by remember { mutableStateOf(false) }

   if (showDeleteTasksDialog) {
      DeleteTasksDialog(
         onDismiss = {
            showDeleteTasksDialog = false
         },
         onConfirm = {
            showDeleteTasksDialog = false
            onDeleteCompletedTasks()
         }
      )
   }

   TopAppBar(
      colors = topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer,
         titleContentColor = MaterialTheme.colorScheme.primary,
      ),
      title = {
         Text(text = "To-Do List")
      },
      actions = {
         IconButton(onClick = onCreateTasks) {
            Icon(
               Icons.Default.Add,
               contentDescription = "Add Tasks"
            )
         }
         IconButton(
            onClick = onRestoreArchive,
            enabled = archivedTasksExist
         ) {
            Icon(
               Icons.Default.Refresh,
               contentDescription = "Restore Archived Tasks"
            )
         }
         IconButton(
            onClick = { showDeleteTasksDialog = true },
            enabled = completedTasksExist
         ) {
            Icon(
               Icons.Default.Delete,
               contentDescription = "Delete"
            )
         }
      })
}

@Preview(showBackground = true)
@Composable
fun ToDoScreenPreview() {
   val viewModel = ToDoViewModel()
   viewModel.createTasks(5)
   ToDoListTheme {
      ToDoScreen(todoViewModel = viewModel)
   }
}