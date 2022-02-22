package com.shahad.app.my_school.ui.studnets

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.ClassList
import com.shahad.app.my_school.data.remote.response.StudentDto
import com.shahad.app.my_school.data.remote.response.UserDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class UsersAdapterRecycler(
    items: List<StudentDto>,
    listener: UserInteractionListener
): BaseRecyclerAdapter<StudentDto>(items,listener) {
    override val layoutId: Int = R.layout.item_user

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<StudentDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface UserInteractionListener: BaseInteractionListener{
}