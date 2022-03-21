package com.shahad.app.my_school.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentMangerProfileBinding
import com.shahad.app.my_school.databinding.FragmentProfileBinding
import com.shahad.app.my_school.databinding.FragmentStudentProfileBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.manger.home.SchoolAdapterRecycler
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MangerProfileFragment: BaseFragment<FragmentMangerProfileBinding>() {

    override fun getLayoutId() = R.layout.fragment_manger_profile
    override val viewModel: MangerProfileViewModel by viewModels()


    override fun onStart() {
        super.onStart()
        viewDataBinding.schools.adapter = SchoolAdapterRecycler(
            viewModel.schools.value ?:  emptyList(),
            viewModel
        )
        observe()
    }


    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@MangerProfileFragment){
                findNavController().navigateUp()
            }
        }
    }

}