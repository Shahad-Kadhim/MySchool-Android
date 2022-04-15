package com.shahad.app.my_school.ui.solution

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDutyBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolutionFragment: BaseFragment<FragmentDutyBinding>() {

    override fun getLayoutId() = R.layout.fragment_solutions
    override val viewModel: SolutionViewModel by viewModels()

}