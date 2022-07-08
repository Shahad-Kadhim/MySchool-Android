package com.shahad.app.my_school.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahad.app.my_school.data.local.entities.ClassEntity
import com.shahad.app.my_school.data.local.entities.SchoolsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MySchoolDao{

    @Query("SELECT * FROM SchoolsEntity")
    fun getSchools() : Flow<List<SchoolsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSchool(items: List<SchoolsEntity>)


    @Query("SELECT * FROM ClassEntity WHERE name LIKE '%' || :name || '%' ")
    fun getCLasses(name: String ="") : Flow<List<ClassEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClasses(items: List<ClassEntity>)


}
