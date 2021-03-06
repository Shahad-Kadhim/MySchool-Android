package com.shahad.app.my_school.ui.home.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.State
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import com.shahad.app.my_school.util.extension.showToast
import com.shahad.app.my_school.util.extension.toHomeItems
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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
            mutableListOf<HomeItem>().apply {
                viewModel.dutiesStatistic.value?.toData()?.data?.apply {
                    add(HomeItem.DutyItem(this))
                }
                add(HomeItem.ClassesLabelItem)
                viewModel.classes.value?.toHomeItems()?.let {
                    addAll(it)
                }

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

            dutiesStatistic.observe(this@HomeStudentFragment){
                if(it is State.Success){
                    it.data?.data?.let { statistic ->
                        (viewDataBinding.classRecycler.adapter as HomeStudentAdapterRecycler)
                            .addDutyStatistic(statistic)
                    }
                }
            }

            unAuthentication.observe(this@HomeStudentFragment){
                it?.let{
                    (requireActivity() as MainActivity).navToIdentity()
                }
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

            clickDutiesEvent.observeEvent(this@HomeStudentFragment){
                viewDataBinding.root.goToFragment(HomeStudentFragmentDirections.actionHomeFragmentToAssignmentStudentFragment())
            }

            message.observe(this@HomeStudentFragment){
                this@HomeStudentFragment.requireContext().showToast(it)
            }
        }
    }

}