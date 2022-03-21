package com.shahad.app.my_school.data.remote.response


data class TeacherDto(
    val id: String ,
    var name: String,
    var password: String,
    var teachingSpecialization: String,
    var phone: Long,
)
