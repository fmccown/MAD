package com.zybooks.petadoption

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zybooks.petadoption.data.AppSettingsRepo

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
   name = "app_prefs"
)

class PetAdoptionApplication: Application() {
   lateinit var appSettingsRepo: AppSettingsRepo

   override fun onCreate() {
      super.onCreate()
      appSettingsRepo = AppSettingsRepo(dataStore)
   }
}