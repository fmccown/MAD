package com.zybooks.todolist.data

import android.content.Context
import android.util.Log
import com.zybooks.todolist.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.io.PrintWriter

class TaskRepository(private val context: Context) {
   private val taskList = mutableListOf<Task>()

   companion object {
      val TODO_FILENAME = "todofile"
   }

   suspend fun loadTasks(): List<Task> {
      withContext(Dispatchers.IO) {
         try {
            val inputStream = context.openFileInput(TODO_FILENAME)
            val reader = inputStream.bufferedReader()

            taskList.clear()

            // Read first line from file
            var line: String? = reader.readLine()

            // Keep reading until no lines remain
            while (line != null) {
               // Convert this line to task ID
               val task = Task()
               task.id = line.toInt()

               // Next line is task body
               line = reader.readLine()
               if (line != null) {
                  task.body = line
               }

               // Next line is "true" or "false"
               line = reader.readLine()
               if (line != null) {
                  task.completed = line == "true"
               }

               // Append each task
               taskList.add(task)

               // Attempt to read next task's ID
               line = reader.readLine()
            }
         } catch (ex: FileNotFoundException) {
            Log.d("TaskRepository", "$TODO_FILENAME does not exist")
         }
      }

      return taskList.map { it.copy() }
   }

   private suspend fun saveTasks() {
      withContext(Dispatchers.IO) {
         val outputStream = context.openFileOutput(TODO_FILENAME, Context.MODE_PRIVATE)
         val writer = PrintWriter(outputStream)

         // Write each task on a separate line
         taskList.forEach { task ->
            writer.println(task.id)
            writer.println(task.body)
            writer.println(task.completed)
         }

         writer.close()
      }
   }

   suspend fun addTask(body: String): Task {
      // Create ID that is one larger than existing IDs
      val taskId = if (taskList.isEmpty()) 1 else taskList.maxOf { it.id } + 1
      val newTask = Task(
         id = taskId,
         body = body
      )
      taskList.add(newTask)

      saveTasks()
      return newTask.copy()
   }

   suspend fun deleteTask(task: Task) {
      taskList.remove(task)
      saveTasks()
   }

   suspend fun deleteCompletedTasks() {
      taskList.removeIf { it.completed }
      saveTasks()
   }

   suspend fun toggleTaskCompleted(task: Task) {
      val index = taskList.indexOf(task)
      taskList[index].completed = !task.completed
      saveTasks()
   }
}