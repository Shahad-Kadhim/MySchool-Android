package com.shahad.app.my_school.ui.duty

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDutyBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DutyFragment: BaseFragment<FragmentDutyBinding>() {

    override fun getLayoutId() = R.layout.fragment_duty
    override val viewModel: DutyViewModel by viewModels()

}