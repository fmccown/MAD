package com.zybooks.todolist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.todolist.Task
import com.zybooks.todolist.data.AppPreferences
import com.zybooks.todolist.data.PrefStorage
import com.zybooks.todolist.data.TaskOrder
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class ToDoViewModel(
   prefStorage: PrefStorage
) : ViewModel() {

   val appPrefs: StateFlow<AppPreferences> = prefStorage
      .appPreferencesFlow
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000),
         initialValue = AppPreferences()
      )

   var taskList = mutableStateListOf<Task>()
      private set

   private val taskListOrig = mutableListOf<Task>()

   private val archivedTasks = mutableListOf<Task>()

   private fun sortList() {
      taskList.clear()
      if (appPrefs.value.taskOrder == TaskOrder.ALPHABETIC) {
         taskList.addAll(taskListOrig.sortedBy { it.body })
      } else if (appPrefs.value.taskOrder == TaskOrder.NEWEST_IS_FIRST) {
         taskList.addAll(taskListOrig.reversed())
      } else {
         taskList.addAll(taskListOrig)
      }
   }

   var confirmDelete by mutableStateOf(true)

   fun addTask(body: String) {
      taskListOrig.add(Task(body = body))
      sortList()
   }

   fun deleteTask(task: Task) {
      taskListOrig.remove(task)
      sortList()
   }

   val archivedTasksExist: Boolean
      get() = archivedTasks.isNotEmpty()

   fun archiveTask(task: Task) {
      // Remove from current task list but archive for later
      taskListOrig.remove(task)
      archivedTasks.add(task)
      sortList()
   }

   val completedTasksExist: Boolean
      get() = taskList.count { it.completed } > 0

   fun deleteCompletedTasks() {
      // Remove only tasks that are completed
      taskListOrig.removeIf { it.completed }
      sortList()
   }

   fun createTasks() {
      // Add tasks for testing purposes
      for (i in 1..appPrefs.value.numTasks) {
         taskListOrig.add(Task(body = "task $i"))
      }
      sortList()
   }

   fun toggleTaskCompleted(task: Task) {
      // Observer of MutableList not notified when changing a property, so
      // need to replace element in the list for notification to go through
      val index = taskListOrig.indexOf(task)
      taskListOrig[index] = taskListOrig[index].copy(completed = !task.completed)
      sortList()
   }

   fun restoreArchivedTasks() {
      // Restore all archived tasks, then clear the list
      taskListOrig.addAll(archivedTasks)
      archivedTasks.clear()
      sortList()
   }

   init {
      viewModelScope.launch {
         prefStorage.appPreferencesFlow.collect {
            confirmDelete = it.confirmDelete
         }
      }
   }
}