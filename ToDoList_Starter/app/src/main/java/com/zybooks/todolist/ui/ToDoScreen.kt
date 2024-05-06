package com.zybooks.todolist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.todolist.Task
import com.zybooks.todolist.ui.theme.ToDoListTheme

@Composable
fun ToDoScreen(
   modifier: Modifier = Modifier,
   todoViewModel: ToDoViewModel = viewModel()
) {

      Column(
         modifier = Modifier
            .fillMaxSize()
      ) {

//         todoViewModel.addTask("Vacuum kitchen")
//         todoViewModel.addTask("Check porch for packages")
//         todoViewModel.addTask("Buy groceries")
//         todoViewModel.addTask("Walk dog")
//         todoViewModel.addTask("Scrub shower stall")
//         todoViewModel.addTask("Water plants")
//         todoViewModel.addTask("Send email to Grandma")
//         todoViewModel.addTask("Remove useless apps from phone")
//         todoViewModel.addTask("Practice guitar")
//         todoViewModel.addTask("Fix bike tire")
//         todoViewModel.addTask("Study for this class")

         AddTaskInput { todoViewModel.addTask(it) }

         LazyColumn {
            items(todoViewModel.taskList) { task ->
               TaskCard(
                  task = task,
                  toggleCompleted = todoViewModel::toggleTaskCompleted
               )
            }
         }
      }
}

@Composable
fun TaskCard(
   task: Task,
   toggleCompleted: (Task) -> Unit,
   modifier: Modifier = Modifier
) {
//   Card(
//      modifier = modifier
//         .padding(8.dp)
//         .fillMaxWidth(),
//      colors = CardDefaults.cardColors(
//         containerColor = MaterialTheme.colorScheme.surfaceVariant
//      )
//   ) {
//      Row(
//         modifier = modifier.fillMaxWidth(),
//         verticalAlignment = Alignment.CenterVertically,
//         horizontalArrangement = Arrangement.SpaceBetween
//      ) {
//         Text(
//            text = task.body,
//            modifier = modifier.padding(start = 12.dp),
//            color = if (task.completed) Color.Gray else Color.Black
//         )
//         Checkbox(
//            checked = task.completed,
//            onCheckedChange = {
//               toggleCompleted(task)
//            }
//         )
//      }
//   }
   Text(
      text = task.body,
      fontSize = 26.sp,
      modifier = modifier.padding(start = 12.dp),
   )}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskInput(onEnterTask: (String) -> Unit) {
   val keyboardController = LocalSoftwareKeyboardController.current

   // state for our new task body
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

@Preview(showBackground = true)
@Composable
fun ToDoScreenPreview() {
   val viewModel = ToDoViewModel()
   viewModel.createTasks(5)
   ToDoListTheme {
      ToDoScreen(todoViewModel = viewModel)
   }
}