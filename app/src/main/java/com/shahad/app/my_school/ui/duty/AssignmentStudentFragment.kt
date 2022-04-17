package com.shahad.app.my_school.ui.duty

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentAssignmentBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssignmentStudentFragment: BaseFragment<FragmentAssignmentBinding>() {

    override fun getLayoutId() = R.layout.fragment_assignment
    override val viewModel: AssignmentStudentViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        observe()
        viewDataBinding.dutyRecycle.adapter = AssignmentAdapterRecycler(listOf(),viewModel)
    }


    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@AssignmentStudentFragment){
                findNavController().navigateUp()
            }
            clickDutyEvent.observeEvent(this@AssignmentStudentFragment){
                viewDataBinding.root.goToFragment(
                    AssignmentStudentFragmentDirections.actionAssignmentStudentFragmentToDutyDetailsFragment(it,Role.TEACHER)
                )
            }
        }
    }
}