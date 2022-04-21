package com.shahad.app.my_school.ui.notification

import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.NotificationDto
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter

class NotificationAdapterRecycler(
    items: List<NotificationDto>,
    listener: NotificationInteractionListener,
): BaseRecyclerAdapter<NotificationDto>(items,listener) {
    override val layoutId: Int = R.layout.item_notification

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<NotificationDto>
    ): Boolean =
        getItems()[oldItemPosition].id == newItems[newItemPosition].id
}
interface NotificationInteractionListener: BaseInteractionListener
