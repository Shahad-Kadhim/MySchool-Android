package com.shahad.app.my_school.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SchoolsEntity (
    @PrimaryKey val id:String,
    var name: String
    )