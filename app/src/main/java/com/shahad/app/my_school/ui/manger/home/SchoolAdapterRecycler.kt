package com.shahad.app.my_school.ui.manger.home

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class SchoolAdapterRecycler(
    items: List<SchoolDto>,
    listener: SchoolInteractionListener
): BaseRecyclerAdapter<SchoolDto>(items,listener) {
    override val layoutId: Int = R.layout.item_school

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<SchoolDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface SchoolInteractionListener: BaseInteractionListener {

}
