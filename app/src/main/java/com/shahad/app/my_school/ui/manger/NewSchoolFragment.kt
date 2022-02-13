package com.shahad.app.my_school.ui.manger

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogNewSchoolBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewSchoolFragment: BaseBottomSheetDialogFragment<FragmentDailogNewSchoolBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_new_school

    override val viewModel: NewSchoolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessCreated.observeEvent(this@NewSchoolFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}