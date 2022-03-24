package com.shahad.app.my_school.ui

import com.shahad.app.my_school.R
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class SchoolAdapterRecycler(
    items: List<School>,
    listener: SchoolInteractionListener,
    itemLayout: Int =  R.layout.item_school
): BaseRecyclerAdapter<School>(items,listener) {
    override val layoutId: Int = itemLayout

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<School>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface SchoolInteractionListener: BaseInteractionListener {

}
