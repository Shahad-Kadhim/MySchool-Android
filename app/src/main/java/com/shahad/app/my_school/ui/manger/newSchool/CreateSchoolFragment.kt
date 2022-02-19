package com.shahad.app.my_school.ui.manger.newSchool

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogNewSchoolBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSchoolFragment: BaseBottomSheetDialogFragment<FragmentDailogNewSchoolBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_new_school

    override val viewModel: CreateSchoolViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.operation= "Create"
        observe()
    }


    private fun observe() {
        with(viewModel){
            onSuccessCreated.observeEvent(this@CreateSchoolFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}