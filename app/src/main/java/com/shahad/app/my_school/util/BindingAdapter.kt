package com.shahad.app.my_school.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Index
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.shahad.app.my_school.R
import com.shahad.app.my_school.data.remote.response.SchoolDto
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.ui.add.post.PostType
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.toPostType


@BindingAdapter(value = ["app:setNumber"])
fun setNumber(view: EditText, number: Long?){
    number?.let {
        view.setText(number.toString())
    }
}

@InverseBindingAdapter(attribute = "setNumber", event="onEnterNumber")
fun getNumber(view: EditText): Long? =
    view.text.toString().toLongOrNull()

@BindingAdapter("onEnterNumber")
fun onEnterEvent(view: EditText, attChange: InverseBindingListener){
    view.doOnTextChanged { text, _, _, _ ->
        text?.takeIf { it.isNotBlank() }?.let {
            attChange.onChange()
        }
    }
}




@BindingAdapter(value = ["selectedItem"], requireAll = false)
fun bindSpinnerData(
    spinner: Spinner,
    newSelectedValue: PostType?
) {
    newSelectedValue?.let {
        val pos = spinner.selectedItemPosition
        spinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedItem", event = "selectedItemAttrChanged")
fun captureSelectedValue(spinner: Spinner): PostType {
    return spinner.selectedItem.toString().uppercase().toPostType()
}

@BindingAdapter("selectedItemAttrChanged")
fun onChange(spinner: Spinner, attChange: InverseBindingListener){
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            attChange.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}


@BindingAdapter(value = ["app:showOnLoading"])
fun <T>showOnLoading(view: View, state: State<T>?){
    view.visibility = if(state is State.Loading) View.VISIBLE else View.GONE
}


@BindingAdapter(value = ["app:showOnSuccess"])
fun <T>showOnSuccess(view: View, state: State<T>?){
    view.visibility = if(state is State.Success) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["app:disableOnLoading"])
fun <T>disableOnLoading(view: View, state: State<T>?){
    view.isEnabled = state !is State.Loading
}


@BindingAdapter(value = ["items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as? BaseRecyclerAdapter<T>)?.setItems(items ?: emptyList())
}


@BindingAdapter(value = ["checkedChip"])
fun setCheckedChip(view: ChipGroup, role:Role?){
    role?.let{
        when(it){
            Role.TEACHER -> check(view,R.id.chip_teacher)
            Role.STUDENT -> check(view,R.id.chip_student)
            Role.MANGER -> check(view,R.id.chip_manger)
        }
    }
}

@InverseBindingAdapter(attribute = "checkedChip",event = "onChipCheckedListener")
fun getCheckedChip(view:ChipGroup): Role? =
    when(view.checkedChipId){
        R.id.chip_teacher-> Role.TEACHER
        R.id.chip_manger-> Role.MANGER
        R.id.chip_student-> Role.STUDENT
        else-> null
    }

@BindingAdapter( "onChipCheckedListener")
fun onChangeChecked(view: ChipGroup, attChange: InverseBindingListener){
    view.setOnCheckedChangeListener { group, checkedId ->
            attChange.onChange()
    }
}

fun check(view: ChipGroup,id: Int){
    view.checkedChipId.takeIf { it!=id }?.let {
        view.check(id)
    }
}
@BindingAdapter(value = ["app:backgroundResource"])
fun setBackgroundDrawableLinear(view: MaterialCardView, value: Boolean ){
    view.setBackgroundResource(R.drawable.background_card)
}

@BindingAdapter(value = ["titles"])
fun setViewPager(tabLayout: TabLayout,viewPager: ViewPager2){
    val tabTitle = listOf("Post", "Members")
    TabLayoutMediator(tabLayout,viewPager){tab ,postion ->
        tab.text= tabTitle[postion]
    }.attach()
}

@BindingAdapter(value = ["app:chipItems"])
fun setChipGroup(view: ChipGroup,items:List<School>?){
    items.takeUnless { it.isNullOrEmpty() }?.let {
        it.forEach {
            view.addView(
                Chip(view.context).apply {
                    text = it.name
                    isClickable =true
                    chipBackgroundColor = ContextCompat.getColorStateList(view.context,R.color.chip_color)
                    setOnClickListener {
                        view.check(this.id)
                    }
                }
            )
        }
        view.check(view.getChildAt(0).id)
    }
}


@BindingAdapter(value = ["checkedChipSchool"])
fun setCheckedChipSchool(view: ChipGroup, schoolName: String?){
}

@InverseBindingAdapter(attribute = "checkedChipSchool",event = "onChipCheckedListener")
fun getCheckedChipSchool(view:ChipGroup): String? =
    (view.children.find {  it.id==(view.checkedChipId)} as Chip).text.toString()


@BindingAdapter(value = ["app:hide"])
fun hideView(view: View, value: Boolean){
    view.visibility= if(value) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter(value= ["isSelected"])
fun setSelectionState(view: ImageView, isSelected: Boolean){
    view.background = if(isSelected) ContextCompat.getDrawable(view.context,R.drawable.selected_shape) else
        ContextCompat.getDrawable(view.context,R.drawable.ic_user)
}

@BindingAdapter(value = ["app:setImageByBitmap"])
fun setImageByBitmap(view: ImageView, bitmap: Bitmap?){
    bitmap?.let {
        view.setImageBitmap(it)
    }
}

@BindingAdapter(value = ["app:hideIfNull"])
fun <T> hideView(view: View , value: T?){
    view.visibility = if(value == null) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["app:hideIfLesson"])
fun  hideView(view: View , type: PostType?){
    view.visibility = when(type){
        PostType.DUTY -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter(value = ["app:onRefresh"])
fun refresh(view: SwipeRefreshLayout,value: Boolean? ){
    value?.let {
        view.isRefreshing = value
    }
}

@InverseBindingAdapter(attribute = "app:onRefresh", event = "onRefresh")
fun getIfRefresh(view: SwipeRefreshLayout): Boolean?{
    return view.isRefreshing
}

@BindingAdapter("onRefresh")
fun onChangeChecked(view: SwipeRefreshLayout, attChange: InverseBindingListener) {
    view.setOnRefreshListener {
        attChange.onChange()
        Log.i("TAG","is Refresh")
    }
}

@BindingAdapter(value = ["app:onSwipe","app:ifSwipe"])
fun onSwipe(view: RecyclerView, onSwipe: (o:Int) -> Unit,isSwipe: Boolean){
    if(isSwipe){

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwipe(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(view)
    }
}

@BindingAdapter(value = ["app:focus"])
fun setFocus(view: EditText, value: String?){
    if(value == null) {
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }
}