package com.shahad.app.my_school.ui.profile

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentTeacherProfileBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.manger.home.SchoolAdapterRecycler
import com.shahad.app.my_school.util.State
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherProfileFragment: BaseFragment<FragmentTeacherProfileBinding>() {

    override fun getLayoutId() = R.layout.fragment_teacher_profile
    override val viewModel: TeacherProfileViewModel by viewModels()
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
            clickBackEvent.observeEvent(this@TeacherProfileFragment){
                findNavController().navigateUp()
            }
        }
    }

}