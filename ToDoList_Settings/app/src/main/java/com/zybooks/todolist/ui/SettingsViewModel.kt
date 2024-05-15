package com.zybooks.todolist.ui

import androidx.lifecycle.ViewModel
import com.zybooks.todolist.data.TaskOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {
   private val _uiState = MutableStateFlow(SettingsUiState())
   val uiState = _uiState.asStateFlow()

   fun setTaskOrder(order: TaskOrder) {

   }
}

data class SettingsUiState (
   val taskOrder: TaskOrder = TaskOrder.NEWEST_IS_LAST,
   val confirmDelete: Boolean = true,
   val numTasks: Int = 10
)