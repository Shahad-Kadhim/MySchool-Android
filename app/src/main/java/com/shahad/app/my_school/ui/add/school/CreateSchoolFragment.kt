package com.shahad.app.my_school.ui.add.school

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogAddBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSchoolFragment: BaseBottomSheetDialogFragment<FragmentDailogAddBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_add

    override val viewModel: CreateSchoolViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.operation= "Create"
        viewDataBinding.type= "School"
        observe()
    }


    private fun observe() {
        with(viewModel){
            onSuccessRequest.observeEvent(this@CreateSchoolFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}