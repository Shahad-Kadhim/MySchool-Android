package com.shahad.app.my_school.ui.profile

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter
import com.shahad.app.my_school.ui.manger.home.SchoolInteractionListener

class SchoolHAdapterRecycler(
    items: List<SchoolDto>,
    listener: SchoolInteractionListener
): BaseRecyclerAdapter<SchoolDto>(items,listener) {
    override val layoutId: Int = R.layout.item_school_h

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<SchoolDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}