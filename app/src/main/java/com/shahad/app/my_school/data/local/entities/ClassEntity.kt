package com.shahad.app.my_school.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClassEntity(
    @PrimaryKey val id: String,
    val name: String,
    val teacherName: String,
    val stage: Int
)
