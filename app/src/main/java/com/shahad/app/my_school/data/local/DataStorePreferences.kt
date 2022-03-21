package com.shahad.app.my_school.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(context: Context){

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore("mySchool")

    private val prefDataStore = context.preferencesDataStore

    fun readTokenPre(): Flow<String?> =
        prefDataStore.data.map { preferences ->
            preferences[tokenKey]
        }

    suspend fun writeTokenPre(token: String){
        prefDataStore.edit { settings ->
            settings[tokenKey] = token
        }
    }

    fun readRolePre(): Flow<String?> =
        prefDataStore.data.map { preferences ->
            preferences[roleKey]
        }

    suspend fun writeRolePre(role: String){
        prefDataStore.edit { settings ->
            settings[roleKey] = role
        }
    }

    companion object{
        val tokenKey = stringPreferencesKey("token")
        val roleKey = stringPreferencesKey("role")
    }
}