package com.shahad.app.my_school.data.remote.response


data class TeacherDto(
    var name: String,
    val id: String ,
    var password: String,
    var teachingSpecialization: String,
    var phone: Int,
)
