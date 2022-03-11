package com.shahad.app.my_school.ui.add.post

data class CreatePostBody private constructor(
    val title: String ,
    val content: String,
    val payload: String?,
    val classId: String,
    val type: String
){
    companion object{
        fun create(
            title: String? ,
            content: String?,
            payload: String?,
            classId: String?,
            type: String?
        ): CreatePostBody? {
            return takeIf { !(title.isNullOrBlank()) &&!(content.isNullOrBlank()) && classId !=null && type !=null}?.let {
                CreatePostBody(title!!,content!!,payload,classId!!,type!!)
            }
        }
    }
}