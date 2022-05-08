package com.shahad.app.my_school.ui.users.teachers

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentUsersBinding
import com.shahad.app.my_school.ui.UserSelectionAdapterRecycler
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
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
        viewDataBinding.studentRecycle.adapter = UserSelectionAdapterRecycler(listOf(),viewModel)
        viewDataBinding.type = "Teachers"
    }


    private fun observe() {
        with(viewModel){
            clickAddTeacherEvent.observeEvent(this@TeachersFragment){
                viewDataBinding.root.goToFragment(
                    TeachersFragmentDirections.actionTeachersFragmentToAddTeacherFragment(it)
                )
            }
            clickBackEvent.observeEvent(this@TeachersFragment){
                findNavController().navigateUp()
            }
            unAuthentication.observe(this@TeachersFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
        }
    }
}