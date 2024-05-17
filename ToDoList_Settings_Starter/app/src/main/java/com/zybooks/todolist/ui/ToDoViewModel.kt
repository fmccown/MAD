package com.zybooks.todolist.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.zybooks.todolist.Task
import com.zybooks.todolist.data.PreferenceStorage

class ToDoViewModel(
   prefStorage: PreferenceStorage
) : ViewModel() {
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

   fun createTestTasks() {
      // Add tasks for testing purposes
      for (i in 1..10) {
         addTask("task $i")
      }
   }

   fun toggleTaskCompleted(task: Task) {
      // Observer of the MutableList not notified when changing a property,
      // so need to replace the list element to notify the observer
      val index = taskList.indexOf(task)
      taskList[index] = taskList[index].copy(completed = !task.completed)
   }

   fun restoreArchivedTasks() {
      // Restore all archived tasks, then clear the list
      taskList.addAll(archivedTasks)
      archivedTasks.clear()
   }
}