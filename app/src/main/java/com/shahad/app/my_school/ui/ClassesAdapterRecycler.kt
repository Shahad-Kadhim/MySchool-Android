package com.shahad.app.my_school.ui

import com.shahad.app.my_school.R
import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class ClassesAdapterRecycler(
    items: List<ClassM>,
    listener: ClassInteractionListener,
    itemLayout: Int = R.layout.item_class_with_stage
): BaseRecyclerAdapter<ClassM>(items,listener) {
    override val layoutId: Int = itemLayout

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<ClassM>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface ClassInteractionListener: BaseInteractionListener{
    fun onClickClass(classId: String,className: String)
}