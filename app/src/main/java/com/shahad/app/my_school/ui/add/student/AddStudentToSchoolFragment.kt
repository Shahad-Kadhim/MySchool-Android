package com.shahad.app.my_school.ui.add.student

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogAddBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddStudentToSchoolFragment: BaseBottomSheetDialogFragment<FragmentDailogAddBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_add

    override val viewModel: AddStudentToSchoolViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.operation= "Add"
        viewDataBinding.type= " Student "
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessJoined.observeEvent(this@AddStudentToSchoolFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}