package com.shahad.app.my_school.ui.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.*
import androidx.fragment.app.Fragment
import com.shahad.app.my_school.BR

abstract class BaseFragment<VDB: ViewDataBinding>: Fragment() {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract val viewModel: BaseViewModel

    private lateinit var _viewDataBinding: VDB
    protected val viewDataBinding
        get() = _viewDataBinding

    protected open fun getViewModelBindingVariable() = BR.viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _viewDataBinding = DataBindingUtil.inflate<VDB>(inflater, getLayoutId(), container, false).also {
            it.setVariable(getViewModelBindingVariable(), viewModel)
            it.lifecycleOwner = viewLifecycleOwner
        }

        return _viewDataBinding.root
    }

}