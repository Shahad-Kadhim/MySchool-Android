package com.shahad.app.my_school.ui.profile

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentProfileBinding
import com.shahad.app.my_school.databinding.FragmentStudentProfileBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentProfileFragment: BaseFragment<FragmentStudentProfileBinding>() {

    override fun getLayoutId() = R.layout.fragment_student_profile
    override val viewModel: StudentProfileViewModel by viewModels()


    override fun onStart() {
        super.onStart()
        viewDataBinding.schools.adapter = SchoolHAdapterRecycler(
            viewModel.schools.value?.toData()?.data ?: emptyList(),
            viewModel
        )
    }
}