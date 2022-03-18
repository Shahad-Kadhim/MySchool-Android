package com.shahad.app.my_school.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahad.app.my_school.data.local.entities.SchoolsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MySchoolDao{

    @Query("SELECT * FROM SchoolsEntity")
    fun getSchools() : Flow<List<SchoolsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSchool(items: List<SchoolsEntity>)

}
