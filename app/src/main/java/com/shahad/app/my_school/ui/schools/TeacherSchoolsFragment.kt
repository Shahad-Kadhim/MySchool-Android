package com.shahad.app.my_school.ui.schools

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentSchoolsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.SchoolAdapterRecycler
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
        viewDataBinding.isAuth = false
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

        }
    }

}