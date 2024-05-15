package com.zybooks.todolist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefStorage(private val context: Context) {
   companion object {
      private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app_prefs")

      private object PreferenceKeys {
         val TASK_ORDER = stringPreferencesKey("taskOrder")
         val CONFIRM_DELETE = booleanPreferencesKey("confirmDelete")
         val NUM_TASKS = intPreferencesKey("numTasks")
      }
   }

   val appPreferencesFlow: Flow<AppPreferences> = context.dataStore.data.map { preferences ->
      val taskOrder = preferences[PreferenceKeys.TASK_ORDER] ?: TaskOrder.NEWEST_IS_LAST.name
      val confirmDelete = preferences[PreferenceKeys.CONFIRM_DELETE] ?: false
      val numTasks = preferences[PreferenceKeys.NUM_TASKS] ?: 10

      AppPreferences(enumValueOf<TaskOrder>(taskOrder), confirmDelete, numTasks)
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

   suspend fun saveNumTasks(numTasks: Int) {
      context.dataStore.edit { preferences ->
         preferences[PreferenceKeys.NUM_TASKS] = numTasks
      }
   }
}