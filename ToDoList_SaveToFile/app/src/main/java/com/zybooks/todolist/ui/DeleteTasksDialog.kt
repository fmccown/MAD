package com.zybooks.todolist.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteTasksDialog(
   onConfirm: () -> Unit,
   onDismiss: () -> Unit
) {
   AlertDialog(
      onDismissRequest = {
         onDismiss()
      },
      title = {
         Text(text = "Delete all completed tasks?")
      },
      confirmButton = {
         Button(
            colors = ButtonDefaults.buttonColors(
               containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
               onConfirm()
            }) {
            Text("Yes")
         }
      },
      dismissButton = {
         Button(
            colors = ButtonDefaults.buttonColors(
               containerColor = MaterialTheme.colorScheme.secondary
            ),
            onClick = {
               onDismiss()
            }) {
            Text("No")
         }
      },
   )
}