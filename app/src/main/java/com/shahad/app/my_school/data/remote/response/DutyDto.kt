package com.shahad.app.my_school.data.remote.response

data class DutyDto(
    val id: String,
    val state: String, // DONE , TO_DO or Missing
    val studentID: String,
    var title: String,
    var content: String,
)
