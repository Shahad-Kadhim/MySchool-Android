package com.shahad.app.my_school.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentProfileBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.util.extension.observeEvent
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
        observe()
    }


    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@StudentProfileFragment){
                findNavController().navigateUp()
            }
        }
    }

}