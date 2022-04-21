package com.shahad.app.my_school.ui.register

data class TeacherRegisterBody (
    var name: String,
    var password: String,
    var teachingSpecialization: String,
    var phone: Long,
    val firebaseToken: String
)