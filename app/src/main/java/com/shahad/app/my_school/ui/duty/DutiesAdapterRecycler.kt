package com.shahad.app.my_school.ui.duty

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.AssignmentDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class AssignmentAdapterRecycler(
    items: List<AssignmentDto>,
    listener: AssignmentInteractionListener,
): BaseRecyclerAdapter<AssignmentDto>(items,listener) {
    override val layoutId: Int = R.layout.item_assignment

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<AssignmentDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id
}
interface AssignmentInteractionListener: BaseInteractionListener{
    fun onClickDuty(dutyId: String)
}
