package com.shahad.app.my_school.ui.home.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeStudentFragment: BaseFragment<FragmentStudentHomeBinding>() {

    override fun getLayoutId() = R.layout.fragment_student_home
    override val viewModel: HomeStudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("TAG","STUDENT")
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.classRecycler.adapter = HomeStudentAdapterRecycler(
            mutableListOf<HomeItem>(
                HomeItem.DutyItem(23,6) ,
                HomeItem.ClassItem(viewModel.classes.value ?: emptyList())
            ),
            viewModel,
            viewModel
        )
    }

    private fun observe() {
        with(viewModel){
            classes.observe(this@HomeStudentFragment){
                Log.i("YYYY",it.toString())
                (viewDataBinding.classRecycler.adapter as HomeStudentAdapterRecycler).editClassItem(HomeItem.ClassItem(it))
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
        }
    }

}