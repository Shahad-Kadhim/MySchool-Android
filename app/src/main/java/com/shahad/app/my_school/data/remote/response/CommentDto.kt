package com.shahad.app.my_school.data.remote.response


data class CommentDto(
    val id: String,
    val postId: String,
    val authorId: String,
    var content: String,
    val dateCommented: String
)
