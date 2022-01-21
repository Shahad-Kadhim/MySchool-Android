package com.shahad.app.my_school

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(context: Context){

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore("settings")

    private val prefDataStore = context.preferencesDataStore

    fun readTokenPre(): Flow<String?> =
        prefDataStore.data.map { preferences ->
            preferences[Token]
        }

    suspend fun writeTokenPre(token: String){
        prefDataStore.edit { settings ->
            settings[Token] = token
        }
    }

    companion object{
        val Token = stringPreferencesKey("token")
    }
}