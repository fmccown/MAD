package com.zybooks.todolist.data

import android.content.Context
import com.zybooks.todolist.Task
import java.io.PrintWriter

class TaskRepository(private val context: Context) {
   val taskList = mutableListOf<Task>()
   private val archivedTasks = mutableListOf<Task>()

   init {
      loadTasks()
   }

   private fun loadTasks() {
      val inputStream = context.openFileInput("todofile")
      val reader = inputStream.bufferedReader()

      // Append each task to stringBuilder
      taskList.clear()
      val line = reader.readLine()
      while (line != null) {
         // Convert this line to task ID
         val task = Task()
         task.id = line.toInt()

         // Next line is task body
         task.body = reader.readLine()

         // Next line is task completed
         task.completed = reader.readLine().toBoolean()

         taskList.add(task)
      }
   }

   fun saveTasks() {
      val outputStream = context.openFileOutput("todofile", Context.MODE_PRIVATE)
      val writer = PrintWriter(outputStream)

      // Write each task on a separate line
      taskList.forEach { task ->
         writer.println(task.id)
         writer.println(task.body)
         writer.println(task.completed)
      }

      writer.close()
   }

   fun addTask(body: String) {
      // Create ID that is one larger than existing IDs
      val taskId = if (taskList.isEmpty()) 1 else taskList.maxOf { it.id } + 1
      taskList.add(
         Task(
            id = taskId,
            body = body
         )
      )
   }

   fun deleteTask(task: Task) {
      taskList.remove(task)
   }

   val archivedTasksExist: Boolean
      get() = archivedTasks.isNotEmpty()

   fun archiveTask(task: Task) {
      // Remove from current task list but archive for later
      taskList.remove(task)
      archivedTasks.add(task)
   }

   val completedTasksExist: Boolean
      get() = taskList.count { it.completed } > 0

   fun deleteCompletedTasks() {
      // Remove only tasks that are completed
      taskList.removeIf { it.completed }
   }

   fun createTestTasks(numTestTasks: Int) {
      // Add tasks for testing purposes
      for (i in 1..numTestTasks) {
         addTask("task $i")
      }
   }

   fun toggleTaskCompleted(task: Task) {
      val index = taskList.indexOf(task)
      taskList[index].completed = !task.completed
   }

   fun restoreArchivedTasks() {
      // Restore all archived tasks, then clear the list
      archivedTasks.forEach { addTask(it.body) }
      archivedTasks.clear()
   }

}