package com.zybooks.petadoption.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val MAX_AGE = 10

class AppSettingsRepo(private val dataStore: DataStore<Preferences>) {
   companion object {
      private val INCLUDE_DOGS_KEY = booleanPreferencesKey("includeDogs")
      private val INCLUDE_CATS_KEY = booleanPreferencesKey("includeCats")
      private val INCLUDE_OTHER_KEY = booleanPreferencesKey("includeOther")
      private val MAX_AGE_KEY = intPreferencesKey("maxAge")
   }

   val includeDogs: Flow<Boolean> = dataStore.data.map { preferences ->
      preferences[INCLUDE_DOGS_KEY] ?: true
   }

   suspend fun saveIncludeDogs(includeDogs: Boolean) {
      dataStore.edit { preferences ->
         preferences[INCLUDE_DOGS_KEY] = includeDogs
      }
   }

   val includeCats: Flow<Boolean> = dataStore.data.map { preferences ->
      preferences[INCLUDE_CATS_KEY] ?: true
   }

   suspend fun saveIncludeCats(includeCats: Boolean) {
      dataStore.edit { preferences ->
         preferences[INCLUDE_CATS_KEY] = includeCats
      }
   }

   val includeOther: Flow<Boolean> = dataStore.data.map { preferences ->
      preferences[INCLUDE_OTHER_KEY] ?: true
   }

   suspend fun saveIncludeOthers(includeOther: Boolean) {
      dataStore.edit { preferences ->
         preferences[INCLUDE_OTHER_KEY] = includeOther
      }
   }

   val maxAge: Flow<Int> = dataStore.data.map { preferences ->
      preferences[MAX_AGE_KEY] ?: MAX_AGE
   }

   suspend fun saveMaxAge(maxAge: Int) {
      dataStore.edit { preferences ->
         preferences[MAX_AGE_KEY] = maxAge
      }
   }

}