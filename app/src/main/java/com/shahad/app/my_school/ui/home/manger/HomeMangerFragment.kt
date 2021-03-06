package com.shahad.app.my_school.ui.home.manger

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentMangerHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.ClassesAdapterRecycler
import com.shahad.app.my_school.ui.SchoolAdapterRecycler
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import com.shahad.app.my_school.util.extension.showToast
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
        viewDataBinding.classRecycler.adapter=
            HomeMangerAdapterRecycler(
                mutableListOf(
                    HomeMangerItem.SchoolsItems(viewModel.schools.value ?: emptyList()),
                    HomeMangerItem.Nav,
                    HomeMangerItem.Classes(viewModel.classes.value ?: emptyList())
                ),
                viewModel,viewModel,viewModel
            )

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
                    HomeMangerFragmentDirections.actionHomeFragmentToClassScreenFragment2(
                        pair.first,
                        pair.second,
                        Role.MANGER
                    )
                )
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
            refreshState.observe(this@HomeMangerFragment){ ifRefresh ->
                takeIf { ifRefresh==true }?.let {
                    refreshClasses()
                    refreshSchools()
                }
            }
            classes.observe(this@HomeMangerFragment){
                (viewDataBinding.classRecycler.adapter as HomeMangerAdapterRecycler).editItems(HomeMangerItem.Classes(it))
            }
            schools.observe(this@HomeMangerFragment){
                (viewDataBinding.classRecycler.adapter as HomeMangerAdapterRecycler).editItems(HomeMangerItem.SchoolsItems(it))
            }
            message.observe(this@HomeMangerFragment){
                this@HomeMangerFragment.requireContext().showToast(it)
            }
        }
    }

}