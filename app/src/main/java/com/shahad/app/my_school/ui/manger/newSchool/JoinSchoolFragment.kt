package com.shahad.app.my_school.ui.manger.newSchool

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogNewSchoolBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinSchoolFragment: BaseBottomSheetDialogFragment<FragmentDailogNewSchoolBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_new_school

    override val viewModel: JoinSchoolViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewDataBinding.operation= "Join"
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessJoined.observeEvent(this@JoinSchoolFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}