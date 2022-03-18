package com.shahad.app.my_school.ui.manger.home

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class ClassesAdapterRecycler(
    items: List<ClassList>,
    listener: ClassInteractionListener
): BaseRecyclerAdapter<ClassList>(items,listener) {
    override val layoutId: Int = R.layout.item_class_with_stage

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<ClassList>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface ClassInteractionListener: BaseInteractionListener{
    fun onClickClass(classId: String,className: String)
}