package com.shahad.app.my_school.ui.manger.schools

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentSchoolsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.manger.home.SchoolAdapterRecycler
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherSchoolsFragment: BaseFragment<FragmentSchoolsBinding>() {

    override fun getLayoutId() = R.layout.fragment_schools
    override val viewModel: SchoolTeacherViewModel by viewModels()

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
    }
    //TODO LATER
    private fun observe() {
        with(viewModel){
//            unAuthentication.observe(this@TeacherSchoolsFragment){
//                (requireActivity() as MainActivity).navToIdentity()
//            }
            clickBackEvent.observeEvent(this@TeacherSchoolsFragment){
                findNavController().navigateUp()
            }
            clickJoinSchoolEvent.observeEvent(this@TeacherSchoolsFragment){
//                viewDataBinding.root.goToFragment(SchoolFragmentDirections.actionSchoolFragmentToNewSchoolFragment())
            }
        }
    }

}