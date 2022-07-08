package com.shahad.app.my_school.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.shahad.app.my_school.data.local.MySchoolDao
import com.shahad.app.my_school.data.local.MySchoolDatabase
import com.shahad.app.my_school.data.local.entities.ClassEntity
import com.shahad.app.my_school.data.local.entities.SchoolsEntity
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MySchoolDaoTest: TestCase() {
    lateinit var dataBase: MySchoolDatabase
    lateinit var dao: MySchoolDao

    @Before
    override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataBase = Room.inMemoryDatabaseBuilder(context, MySchoolDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = dataBase.MySchoolDao()
    }

    @After
    override fun tearDown() {
        dataBase.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun should_SchoolsAdded_When_InsertIt() = runBlockingTest {
        //given
        val school  = listOf(
            SchoolsEntity("1","School1"),
            SchoolsEntity("2","School2"),
        )
        //when
        dao.addSchool(school)
        val schools = dao.getSchools().first()
        //then

    }


    @ExperimentalCoroutinesApi
    @Test
    fun should_ClassesAdded_When_InsertIt() = runBlockingTest {
        //given
        val classes  = listOf(
            ClassEntity("1","Class1","teacher",2),
            ClassEntity("2","Class2","teacher",4),
        )
        //when
        dao.addClasses(classes)
        val schools = dao.getCLasses().first()
        //then


    }



}