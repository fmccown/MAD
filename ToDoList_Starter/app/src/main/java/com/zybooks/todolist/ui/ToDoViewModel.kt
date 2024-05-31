package com.zybooks.todolist.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.todolist.Task

class ToDoViewModel : ViewModel() {
   val taskList = mutableStateListOf<Task>()

   private val archivedTasks = mutableListOf<Task>()

   fun addTask(body: String) {
      taskList.add(Task(body = body))
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

   fun createTestTasks(numTasks: Int = 10) {
      // Add tasks for testing purposes
      for (i in 1..numTasks) {
         addTask("task $i")
      }
   }

   fun toggleTaskCompleted(task: Task) {
      // Observer of MutableList not notified when changing a property, so
      // need to replace element in the list for notification to go through
      val index = taskList.indexOf(task)
      taskList[index] = taskList[index].copy(completed = !task.completed)
   }

   fun restoreArchivedTasks() {
      // Restore all archived tasks, then clear the list
      archivedTasks.forEach { addTask(it.body) }
      archivedTasks.clear()
   }
}