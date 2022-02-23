package com.shahad.app.my_school.ui.users.teachers

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentUsersBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.users.UsersAdapterRecycler
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeachersFragment: BaseFragment<FragmentUsersBinding>() {

    override fun getLayoutId() = R.layout.fragment_users
    override val viewModel: TeachersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.studentRecycle.adapter = UsersAdapterRecycler(listOf(),viewModel)
        viewDataBinding.type = "Teachers"
    }


    private fun observe() {
        with(viewModel){
            clickAddTeacherEvent.observeEvent(this@TeachersFragment){
                viewDataBinding.root.goToFragment(
                    TeachersFragmentDirections.actionTeachersFragmentToAddTeacherFragment(it)
                )
            }
        }
    }
}