package com.shahad.app.my_school.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClassList(
    @PrimaryKey val id: String,
    val name: String,
    val teacherName: String,
    val stage: Int
)
