package com.zybooks.todolist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.todolist.Task
import com.zybooks.todolist.data.PrefStorage
import com.zybooks.todolist.data.TaskOrder
import kotlinx.coroutines.launch

class ToDoViewModel(
   prefStorage: PrefStorage
) : ViewModel() {

   var taskList = mutableStateListOf<Task>()
      private set

   private val archivedTasks = mutableListOf<Task>()

   private var taskOrder = TaskOrder.NEWEST_IS_LAST

   private fun sortList() {
      when (taskOrder) {
         TaskOrder.ALPHABETIC -> taskList.sortBy { it.body }
         TaskOrder.NEWEST_IS_FIRST -> taskList.sortByDescending { it.id }
         else -> taskList.sortBy { it.id }
      }
   }

   var confirmDelete by mutableStateOf(true)

   private var numTestTasks: Int = 10

   fun addTask(body: String) {
      // Create ID that is one larger than existing IDs
      val taskId = if (taskList.isEmpty()) 1 else taskList.maxOf { it.id } + 1
      taskList.add(Task(
         id = taskId,
         body = body
      ))

      sortList()
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
      for (i in 1..numTestTasks) {
         addTask("task $i")
      }
   }

   fun toggleTaskCompleted(task: Task) {
      // Observer of MutableList is not notified when changing a property,
      // so need to replace the element for observer to be notified
      val index = taskList.indexOf(task)
      taskList[index] = taskList[index].copy(completed = !task.completed)
   }

   fun restoreArchivedTasks() {
      // Restore all archived tasks, then clear the list
      taskList.addAll(archivedTasks)
      archivedTasks.clear()
      sortList()
   }

   init {
      // Get app settings
      viewModelScope.launch {
         prefStorage.appPreferencesFlow.collect {
            confirmDelete = it.confirmDelete
            numTestTasks = it.numTestTasks
            taskOrder = it.taskOrder
            sortList()
         }
      }
   }
}