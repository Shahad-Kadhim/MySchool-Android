package com.shahad.app.my_school.ui.users

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.StudentDto
import com.shahad.app.my_school.data.remote.response.UserDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class UsersAdapterRecycler(
    items: List<UserDto>,
    listener: UserInteractionListener
): BaseRecyclerAdapter<UserDto>(items,listener) {
    override val layoutId: Int = R.layout.item_user

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<UserDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id

}

interface UserInteractionListener: BaseInteractionListener{
}