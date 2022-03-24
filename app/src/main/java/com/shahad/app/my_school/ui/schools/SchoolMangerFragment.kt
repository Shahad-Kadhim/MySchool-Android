package com.shahad.app.my_school.ui.schools

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentSchoolsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.SchoolAdapterRecycler
import com.shahad.app.my_school.util.extension.goToFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolMangerFragment: BaseFragment<FragmentSchoolsBinding>() {

    override fun getLayoutId() = R.layout.fragment_schools
    override val viewModel: SchoolMangerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.isAuth= true
        recycler()
    }
    private fun recycler() {
        viewDataBinding.schoolRecycler.adapter= SchoolAdapterRecycler(emptyList(),viewModel)
    }

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@SchoolMangerFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
            clickBackEvent.observeEvent(this@SchoolMangerFragment){
                findNavController().navigateUp()
            }
            clickCreateSchoolEvent.observeEvent(this@SchoolMangerFragment){
                viewDataBinding.root.goToFragment(SchoolMangerFragmentDirections.actionSchoolFragmentToNewSchoolFragment())
            }
        }
    }

}