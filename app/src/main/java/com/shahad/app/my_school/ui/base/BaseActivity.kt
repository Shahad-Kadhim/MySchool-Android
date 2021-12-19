package com.shahad.app.my_school.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.shahad.app.my_school.BR

abstract class BaseActivity<VDB: ViewDataBinding>: AppCompatActivity() {

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract val viewModel: BaseViewModel

    private lateinit var _viewDataBinding: VDB
    protected val viewDataBinding
        get() = _viewDataBinding

    protected open fun getViewModelBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewDataBinding = DataBindingUtil.setContentView<VDB>(this,getLayoutId()).also {
            it.setVariable(getViewModelBindingVariable(), viewModel)
            it.lifecycleOwner = this
        }
    }

}