package com.shahad.app.my_school.data.remote.response


data class StudentDto (
    val id: String,
    var name: String,
    var password: String,
    var age: Int,
    var note: String,
    var phone: Int ,
    var stage: Int
)