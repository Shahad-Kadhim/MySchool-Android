package com.shahad.app.my_school.ui.classScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentClassScreenBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassScreenFragment: BaseFragment<FragmentClassScreenBinding>() {

    override fun getLayoutId() = R.layout.fragment_class_screen
    override val viewModel: ClassViewModel by viewModels()

    val args: ClassScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.pager.adapter =ClassPagerAdapter(this)
        viewDataBinding.className= args.className
    }

}