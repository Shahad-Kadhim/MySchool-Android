package com.shahad.app.my_school.ui.home.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentStudentHomeBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
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

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@HomeStudentFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
        }
    }

}