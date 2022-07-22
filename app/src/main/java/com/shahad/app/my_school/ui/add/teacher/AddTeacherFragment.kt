package com.shahad.app.my_school.ui.add.teacher

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogAddBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTeacherFragment: BaseBottomSheetDialogFragment<FragmentDailogAddBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_add

    override val viewModel: AddTeacherViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.operation= "Add"
        viewDataBinding.type= " Teacher "
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessRequest.observeEvent(this@AddTeacherFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}