package com.shahad.app.my_school.data.remote.response


data class CommentDto(
    val id: String,
    val postId: String,
    val authorName: String,
    var content: String,
    val dateCommented: Long
)
