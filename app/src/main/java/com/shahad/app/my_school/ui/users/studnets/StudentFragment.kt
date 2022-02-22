package com.shahad.app.my_school.ui.users.studnets

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.users.UsersAdapterRecycler
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentFragment: BaseFragment<FragmentStudentsBinding>() {

    override fun getLayoutId() = R.layout.fragment_students
    override val viewModel: StudentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.studentRecycle.adapter = UsersAdapterRecycler(listOf(),viewModel)
        viewDataBinding.type = "Student"
    }


    private fun observe() {
        with(viewModel){
            clickAddStudentEvent.observeEvent(this@StudentFragment){
                viewDataBinding.root.goToFragment(
                    StudentFragmentDirections.actionSecondFragmentToAddStudentFragment(it)
                )
            }
        }
    }
}