package com.shahad.app.my_school.ui.studnets

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentsBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentFragment: BaseFragment<FragmentStudentsBinding>() {

    override fun getLayoutId() = R.layout.fragment_students
    override val viewModel: StudentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        viewDataBinding.studentRecycle.adapter = UsersAdapterRecycler(listOf(),viewModel)
    }


    private fun observe() {
        with(viewModel){
            schoolName.observe(this@StudentFragment){
                Log.i("TAG",it.toString())
            }
        }
    }
}