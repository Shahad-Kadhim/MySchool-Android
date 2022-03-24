package com.shahad.app.my_school.ui.classes

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentClassesMangerBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.ui.ClassesAdapterRecycler
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassesMangerFragment: BaseFragment<FragmentClassesMangerBinding>() {

    override fun getLayoutId() = R.layout.fragment_classes_manger
    override val viewModel: ClassesMangerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onStart() {
        super.onStart()
        recycler()
    }
    private fun recycler() {
        viewDataBinding.classRecycler.adapter= ClassesAdapterRecycler(emptyList(),viewModel)
    }

    private fun observe() {
        with(viewModel){
            unAuthentication.observe(this@ClassesMangerFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
            clickBackEvent.observeEvent(this@ClassesMangerFragment){
                findNavController().navigateUp()
            }
        }
    }

}