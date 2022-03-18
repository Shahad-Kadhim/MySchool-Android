package com.shahad.app.my_school.ui.manger.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentMangerHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.ui.teacher.home.HomeTeacherFragmentDirections
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMangerFragment: BaseFragment<FragmentMangerHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_manger_home
    override val viewModel: HomeMangerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        recycler()
    }

    private fun recycler() {
        viewDataBinding.schoolRecycler.adapter= SchoolAdapterRecycler(emptyList(),viewModel)
        viewDataBinding.classRecycler.adapter= ClassesAdapterRecycler(emptyList(),viewModel)
    }

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@HomeMangerFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
            clickCreateSchoolEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(HomeMangerFragmentDirections.actionHomeFragmentToNewSchoolFragment())
            }
            clickSchoolsEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(HomeMangerFragmentDirections.actionHomeFragmentToSchoolFragment())
            }
            clickClassesEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(HomeMangerFragmentDirections.actionHomeFragmentToClassesMangerFragment())
            }
            clickStudentEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(HomeMangerFragmentDirections.actionHomeFragmentToSecondFragment())
            }
            clickTeachersEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(HomeMangerFragmentDirections.actionHomeFragmentToTeachersFragment())
            }
            clickClassEvent.observeEvent(this@HomeMangerFragment){ pair ->
                viewDataBinding.root.goToFragment(
                    HomeMangerFragmentDirections.actionHomeFragmentToClassScreenFragment2(pair.first,pair.second, Role.MANGER))
            }
            clickNotificationEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(
                    HomeMangerFragmentDirections.actionHomeMangerFragmentToNotificationFragment2()
                )
            }
            clickProfileEvent.observeEvent(this@HomeMangerFragment){
                viewDataBinding.root.goToFragment(
                    HomeMangerFragmentDirections.actionHomeMangerFragmentToProfileFragment()
                )
            }
        }
    }

}