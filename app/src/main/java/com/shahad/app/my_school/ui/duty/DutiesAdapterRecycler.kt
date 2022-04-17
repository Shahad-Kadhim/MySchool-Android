package com.shahad.app.my_school.ui.duty

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.DutyDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class DutiesAdapterRecycler(
    items: List<DutyDto>,
    listener: AssignmentInteractionListener,
): BaseRecyclerAdapter<DutyDto>(items,listener) {
    override val layoutId: Int = R.layout.item_assignment

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<DutyDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id
}
interface AssignmentInteractionListener: BaseInteractionListener{
    fun onClickDuty(dutyId: String)
}
