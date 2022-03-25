package com.example.models

import com.shahad.app.my_school.data.remote.response.CommentDto
import com.shahad.app.my_school.ui.add.post.PostType


data class PostDetailsDto (
    val id: String,
    var title: String,
    var content: String,
    val payload: String?,
    val authorName: String,
    val datePosted: Long,
    val type: PostType,
    val comments: List<CommentDto>
)