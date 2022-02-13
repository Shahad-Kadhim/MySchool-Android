package com.shahad.app.my_school.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shahad.app.my_school.BR
import com.shahad.app.my_school.R

abstract class BaseBottomSheetDialogFragment<VDB: ViewDataBinding>(): BottomSheetDialogFragment() {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract val viewModel: BaseViewModel

    private lateinit var _viewDataBinding: VDB
    protected val viewDataBinding
        get() = _viewDataBinding

    protected open fun getViewModelBindingVariable() = BR.viewModelBottom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<VDB>(inflater, getLayoutId(), container, false).apply {
            _viewDataBinding= this
            lifecycleOwner = viewLifecycleOwner
            setVariable(getViewModelBindingVariable(), viewModel)
        }.root

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }
}

