package com.shahad.app.my_school.ui.postDetails

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.CommentDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class CommentAdapterRecycler(
    items: List<CommentDto>,
    listener: CommentInteractionListener,
    itemLayout: Int =  R.layout.item_comment
): BaseRecyclerAdapter<CommentDto>(items,listener) {
    override val layoutId: Int = itemLayout

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<CommentDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface CommentInteractionListener: BaseInteractionListener
