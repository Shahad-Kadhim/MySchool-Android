package com.shahad.app.my_school.ui.solution

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.DutySubmit
import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class SolutionsAdapterRecycler(
    items: List<DutySubmit>,
    listener: SolutionInteractionListener,
): BaseRecyclerAdapter<DutySubmit>(items,listener) {
    override val layoutId: Int = R.layout.item_solution

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<DutySubmit>
    ): Boolean =
        getItems()[oldItemPosition].studentId == newItems[newItemPosition].studentId
}
interface SolutionInteractionListener: BaseInteractionListener
