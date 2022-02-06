package com.shahad.app.my_school.ui.register

data class StudentRegisterBody (
    var name: String,
    var password: String,
    var phone: Long,
    var note: String,
    var age: Int,
    var stage: Int,
)