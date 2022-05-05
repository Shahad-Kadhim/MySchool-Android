package com.shahad.app.my_school.ui.home.manger

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.shahad.app.my_school.BR
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.ui.ClassesAdapterRecycler
import com.shahad.app.my_school.ui.SchoolAdapterRecycler
import com.shahad.app.my_school.ui.SchoolInteractionListener
import com.shahad.app.my_school.ui.base.BaseInteractionListener
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter
import com.shahad.app.my_school.ui.base.MySchoolDiffUtil

class HomeMangerAdapterRecycler(
    private val itemsH: MutableList<HomeMangerItem>,
    private val schoolListener: SchoolInteractionListener,
    private val classListener: ClassInteractionListener,
    private val listener: HomeMangerInteractionListener
    ): BaseRecyclerAdapter<HomeMangerItem>(itemsH,listener) {
    override var layoutId: Int = 0

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<HomeMangerItem>
    ): Boolean =
        newItems[newItemPosition].rank == getItems()[oldItemPosition].rank


    override fun areContentSame(
        oldPosition: Int,
        newPosition: Int,
        newList: List<HomeMangerItem>
    ): Boolean {
        return false
    }

    fun editItems(item: HomeMangerItem){
        val newItems =
            mutableListOf<HomeMangerItem>().apply {
                addAll(itemsH)
                when(item){
                    is HomeMangerItem.Classes -> {
                        map{
                            if (it is HomeMangerItem.Classes)
                                it.list = item.list
                        }
                    }
                    HomeMangerItem.Nav -> {
                        add(HomeMangerItem.Nav)
                    }
                    is HomeMangerItem.SchoolsItems -> {
                        map {
                            if (it is HomeMangerItem.SchoolsItems)
                                it.list = item.list
                        }
                }
            }
        }
        val diffResult = DiffUtil.calculateDiff(
            MySchoolDiffUtil(
                itemsH,
            newItems,
            ::areItemsTheSame,
            ::areContentSame)
        )
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        layoutId = getLayout(viewType)
        return super.onCreateViewHolder(parent, viewType)
    }

    private fun getLayout(viewType: Int): Int =
        when (viewType) {
            TYPE_SCHOOLS -> R.layout.item_schools
            TYPE_CLASSES -> R.layout.item_classesl
            else -> R.layout.item_nav_icon
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder as ItemViewHolder, position)
    }

    private fun bind(holder: ItemViewHolder, position: Int) {
        when (val currentItem = itemsH[position]) {
            is HomeMangerItem.Classes -> {
                holder.binding.setVariable(
                    BR.adapter,
                    ClassesAdapterRecycler(
                        items = currentItem.list,
                        listener = classListener
                    )
                )
                holder.binding.setVariable(BR.isEmpty,currentItem.list.isEmpty())
            }

            HomeMangerItem.Nav -> {
                holder.binding.setVariable(BR.listener,listener)
            }

            is HomeMangerItem.SchoolsItems -> {
                holder.binding.setVariable(
                    BR.adapter,
                    SchoolAdapterRecycler(
                        items = currentItem.list,
                        listener = schoolListener
                    )
                )
                holder.binding.setVariable(BR.isEmpty,currentItem.list.isEmpty())
            }

        }
    }


    override fun getItemViewType(position: Int): Int =
        when (itemsH[position]) {
            is HomeMangerItem.Classes -> TYPE_CLASSES
            HomeMangerItem.Nav -> TYPE_NAV
            is HomeMangerItem.SchoolsItems -> TYPE_SCHOOLS
        }

    companion object {
        const val TYPE_CLASSES = 2
        const val TYPE_SCHOOLS = 1
        const val TYPE_NAV = 3

    }

}

interface HomeMangerInteractionListener: BaseInteractionListener{
    fun onClickSchools()
    fun onClickClasses()
    fun onClickTeachers()
    fun onClickStudent()
}