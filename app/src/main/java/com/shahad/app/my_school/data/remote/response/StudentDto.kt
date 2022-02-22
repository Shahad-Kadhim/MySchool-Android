package com.shahad.app.my_school.data.remote.response


data class StudentDto (
    val id: String,
    var name: String,
    var age: Int,
    var note: String,
    var phone: Long ,
    var stage: Int
)