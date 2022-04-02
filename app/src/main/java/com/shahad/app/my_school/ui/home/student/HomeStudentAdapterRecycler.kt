package com.shahad.app.my_school.ui.home.student

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.shahad.app.my_school.BR
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.ui.ClassesAdapterRecycler
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter
import com.shahad.app.my_school.ui.base.MySchoolDiffUtil

class HomeStudentAdapterRecycler(
    private val itemsH: MutableList<HomeItem>,
    private val listener: HomeStudentInteractionListener,
    private val classListener: ClassInteractionListener
): BaseRecyclerAdapter<HomeItem>(itemsH,listener) {
    override var layoutId: Int = 0


    fun addItem(newItems: HomeItem) {
        val newItemsList = itemsH.apply {
            add(newItems)
        }
        val diffResult = DiffUtil.calculateDiff(MySchoolDiffUtil(itemsH,
            newItemsList,
            ::areItemsTheSame,
            ::areContentSame))
        diffResult.dispatchUpdatesTo(this)
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<HomeItem>
    ): Boolean =
        true


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        layoutId = getLayout(viewType)
        return super.onCreateViewHolder(parent, viewType)
    }

    private fun getLayout(viewType: Int): Int =
        when (viewType) {
            TYPE_DUTY -> R.layout.item_duties
            else -> R.layout.item_classes
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder as ItemViewHolder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int) {
        when (val currentItem = itemsH[position]) {
            is HomeItem.ClassItem ->{
                Log.i("TAG_CLASS","$position")
                holder.binding.setVariable(BR.adapter,ClassesAdapterRecycler(currentItem.classes,classListener,R.layout.item_class))
            }
            is HomeItem.DutyItem -> {
                holder.binding.setVariable(BR.numberOfDuty,"${currentItem.numberOfDuty}")
                holder.binding.setVariable(BR.dutyFinished,"${currentItem.dutyComplete}")
                holder.binding.setVariable(BR.listener,listener)
                Log.i("TAG_DUTY","$position")

            }
        }
    }


    override fun getItemViewType(position: Int): Int =
        when (itemsH[position]) {
            is HomeItem.ClassItem -> TYPE_CLASS
            is HomeItem.DutyItem -> TYPE_DUTY
        }

    companion object {
        const val TYPE_CLASS = 2
        const val TYPE_DUTY = 1
    }

}

interface HomeStudentInteractionListener: BaseInteractionListener{
    fun onClickClass(classId: String,className: String)
}