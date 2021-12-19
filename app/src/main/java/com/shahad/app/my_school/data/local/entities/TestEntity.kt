package com.shahad.app.my_school.data.local.entities

import androidx.room.*

//delete after create any entity (just for test)
@Entity
data class TestEntity(
    @PrimaryKey val id: Long= 0,
    val name: String
)