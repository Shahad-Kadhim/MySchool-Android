package com.shahad.app.my_school.data.local

import androidx.room.*
import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.local.entities.SchoolsEntity
import com.shahad.app.my_school.data.remote.response.ClassList

@TypeConverters(Convertor::class)
@Database(entities = [ClassList::class,SchoolsEntity::class] , version = 1)
abstract class MySchoolDatabase: RoomDatabase() {

    abstract fun MySchoolDao(): MySchoolDao

    companion object{
        const val DATABASE_NAME = "MY_SCHOOL"
    }

}