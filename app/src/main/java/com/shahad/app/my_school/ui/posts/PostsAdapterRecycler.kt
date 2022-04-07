package com.shahad.app.my_school.ui.posts

import android.view.ViewGroup
import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.PostDto
import com.shahad.app.my_school.ui.add.post.PostType
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter
import com.shahad.app.my_school.ui.home.student.HomeItem
import com.shahad.app.my_school.ui.home.student.HomeStudentAdapterRecycler

class PostsAdapterRecycler(
    items: List<PostDto>,
    listener: PostInteractionListener
): BaseRecyclerAdapter<PostDto>(items,listener) {
    override var layoutId: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        layoutId = getLayout(viewType)
        return super.onCreateViewHolder(parent, viewType)
    }

    private fun getLayout(viewType: Int): Int =
        when (viewType) {
            TYPE_DUTY -> R.layout.item_duty
            else -> R.layout.item_post
        }

    override fun getItemViewType(position: Int): Int =
        when (getItems()[position].type) {
            PostType.LESSON -> TYPE_Lesson
            PostType.DUTY -> TYPE_DUTY
        }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<PostDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

    companion object {
        const val TYPE_Lesson = 2
        const val TYPE_DUTY = 1
    }
}

interface PostInteractionListener: BaseInteractionListener{
    fun onClickLesson(lessonId: String)
    fun onClickDuty(dutyId: String)

}