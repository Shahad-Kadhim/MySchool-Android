package com.shahad.app.my_school.ui.add.student

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentSelectStudentBinding
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.UserSelectionAdapterRecycler
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectStudentFragment: BaseFragment<FragmentSelectStudentBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_select_student

    override val viewModel: SelectionStudentsViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.studentRecycle.adapter =  UserSelectionAdapterRecycler(emptyList(), viewModel)
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessJoined.observeEvent(this@SelectStudentFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
            clickBackEvent.observeEvent(this@SelectStudentFragment){
                findNavController().navigateUp()
            }
        }
    }


}