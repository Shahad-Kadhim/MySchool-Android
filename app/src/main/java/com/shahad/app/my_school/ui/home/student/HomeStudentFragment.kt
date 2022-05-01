package com.shahad.app.my_school.ui.home.student

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import com.shahad.app.my_school.util.extension.showToast
import com.shahad.app.my_school.util.extension.toHomeItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeStudentFragment: BaseFragment<FragmentStudentHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_student_home
    override val viewModel: HomeStudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.classRecycler.adapter = HomeStudentAdapterRecycler(
            mutableListOf(
                HomeItem.DutyItem(23,6),
                HomeItem.ClassesLabelItem
            ).apply {
                addAll(viewModel.classes.value?.toHomeItems() ?: emptyList())
            },
            viewModel,
        )
    }

    private fun observe() {
        with(viewModel){
            classes.observe(this@HomeStudentFragment){
                (viewDataBinding.classRecycler.adapter as HomeStudentAdapterRecycler)
                    .editClassItem(it.toHomeItems())
            }
            unAuthentication.observe(this@HomeStudentFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
            refreshState.observe(this@HomeStudentFragment){ ifRefresh ->
                takeIf { ifRefresh==true }?.refreshClasses()
            }
            clickProfileEvent.observeEvent(this@HomeStudentFragment){
                viewDataBinding.root.goToFragment(
                    HomeStudentFragmentDirections.actionHomeFragmentToProfileFragment()
                )
            }
            clickClassEvent.observeEvent(this@HomeStudentFragment){ pair ->
                viewDataBinding.root.goToFragment(
                    HomeStudentFragmentDirections.actionHomeFragmentToClassScreen(
                        pair.first,
                        pair.second,
                        Role.STUDENT
                    )
                )
            }
            message.observe(this@HomeStudentFragment){
                this@HomeStudentFragment.requireContext().showToast(it)
            }
        }
    }

}