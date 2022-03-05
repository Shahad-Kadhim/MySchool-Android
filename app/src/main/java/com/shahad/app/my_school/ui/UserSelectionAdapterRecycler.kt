package com.shahad.app.my_school.ui

import com.shahad.app.my_school.R
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class UserSelectionAdapterRecycler(
    students: List<UserSelected>,
    listener: UserSelectedInteractionListener
): BaseRecyclerAdapter<UserSelected>(students,listener) {
    override val layoutId: Int = R.layout.item_student_selection

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<UserSelected>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentSame(
        oldPosition: Int,
        newPosition: Int,
        newList: List<UserSelected>
    ): Boolean =
        getItems()[oldPosition].isSelected == newList[newPosition].isSelected

}

interface UserSelectedInteractionListener: BaseInteractionListener{
    fun onClickSelect(id: String)
}