package com.shahad.app.my_school.ui.classScreen

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentClassScreenBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassScreenFragment: BaseFragment<FragmentClassScreenBinding>() {

    override fun getLayoutId() = R.layout.fragment_class_screen
    override val viewModel: ClassViewModel by viewModels()

}