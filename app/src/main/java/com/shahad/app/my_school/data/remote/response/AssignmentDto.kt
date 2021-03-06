package com.shahad.app.my_school.data.remote.response

data class AssignmentDto(
    val id: String,
    var title: String,
    var content: String,
    val payload: String?,
    val authorName: String,
    val datePosted: Long,
    val degree: Int?,
)