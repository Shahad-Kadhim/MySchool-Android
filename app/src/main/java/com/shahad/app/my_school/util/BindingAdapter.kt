package com.shahad.app.my_school.util

import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.base.BaseRecyclerAdapter
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.toRole

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
    newSelectedValue: Role?
) {
    newSelectedValue?.let {
        val pos = spinner.selectedItemPosition
        spinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedItem", event = "selectedItemAttrChanged")
fun captureSelectedValue(spinner: Spinner): Role {
    return spinner.selectedItem.toString().uppercase().toRole()
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
