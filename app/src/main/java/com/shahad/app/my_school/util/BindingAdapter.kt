package com.shahad.app.my_school.util

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

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