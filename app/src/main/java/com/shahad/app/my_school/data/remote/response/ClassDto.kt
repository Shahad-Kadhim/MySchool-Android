package com.shahad.app.my_school.data.remote.response

data class ClassDto (
    val id: String,
    var name:String,
    var teacherId: String ,
    var schoolId: String ,
    val stage :Int?,
)

