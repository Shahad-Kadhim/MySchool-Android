package com.shahad.app.my_school.ui.solution

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentSolutionsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.users.students.StudentFragmentDirections
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SolutionFragment: BaseFragment<FragmentSolutionsBinding>() {

    override fun getLayoutId() = R.layout.fragment_solutions
    override val viewModel: SolutionViewModel by viewModels()


    override fun onStart() {
        super.onStart()
        observe()
        viewDataBinding.solutionRecycle.adapter = SolutionsAdapterRecycler(listOf(),viewModel)
    }


    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@SolutionFragment){
                findNavController().navigateUp()
            }
        }
    }
}