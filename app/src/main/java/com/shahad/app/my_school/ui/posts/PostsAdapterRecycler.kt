package com.shahad.app.my_school.ui.posts

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.PostDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class PostsAdapterRecycler(
    items: List<PostDto>,
    listener: PostInteractionListener
): BaseRecyclerAdapter<PostDto>(items,listener) {
    override val layoutId: Int = R.layout.item_post

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<PostDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface PostInteractionListener: BaseInteractionListener{
}