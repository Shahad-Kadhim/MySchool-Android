package com.shahad.app.my_school.ui.members

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentMemberBinding
import com.shahad.app.my_school.ui.UserSelectionAdapterRecycler
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.classScreen.ClassScreenFragmentDirections
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberFragment: BaseFragment<FragmentMemberBinding>() {

    override fun getLayoutId() = R.layout.fragment_member
    override val viewModel: MemberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.isAuth = (arguments?.getSerializable("ROLE") as Role) == Role.TEACHER
        arguments?.getString("ID")?.let {
            viewModel.getMembers(it)
        }
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.studentRecycle.adapter =  UserSelectionAdapterRecycler(emptyList(), viewModel)
    }

    private fun observe() {
        with(viewModel){
            clickAddStudentEvent.observeEvent(this@MemberFragment){
                viewDataBinding.root.goToFragment(ClassScreenFragmentDirections.actionClassScreenFragmentToSelectStudentFragment(it))
            }
        }
    }
}