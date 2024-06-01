package com.zybooks.todolist.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class PreferenceStorage(private val context: Context) {
   companion object {
      private val Context.dataStore by preferencesDataStore("app_prefs")

      private object PreferenceKeys {
         val TASK_ORDER = stringPreferencesKey("taskOrder")
         val CONFIRM_DELETE = booleanPreferencesKey("confirmDelete")
         val NUM_TEST_TASKS = intPreferencesKey("numTestTasks")
      }
   }

   val appPreferencesFlow = context.dataStore.data.map { preferences ->
      val taskOrder = preferences[PreferenceKeys.TASK_ORDER] ?: TaskOrder.NEWEST_IS_LAST.name
      val confirmDelete = preferences[PreferenceKeys.CONFIRM_DELETE] ?: true
      val numTestTasks = preferences[PreferenceKeys.NUM_TEST_TASKS] ?: 10

      AppPreferences(enumValueOf<TaskOrder>(taskOrder), confirmDelete, numTestTasks)
   }

   suspend fun saveTaskOrder(order: TaskOrder) {
      context.dataStore.edit { preferences ->
         preferences[PreferenceKeys.TASK_ORDER] = order.name
      }
   }

   suspend fun saveConfirmDelete(confirm: Boolean) {
      context.dataStore.edit { preferences ->
         preferences[PreferenceKeys.CONFIRM_DELETE] = confirm
      }
   }

   suspend fun saveNumTestTasks(numTasks: Int) {
      context.dataStore.edit { preferences ->
         preferences[PreferenceKeys.NUM_TEST_TASKS] = numTasks
      }
   }
}