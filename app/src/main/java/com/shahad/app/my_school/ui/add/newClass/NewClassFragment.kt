package com.shahad.app.my_school.ui.add.newClass

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentDailogNewClassBinding
import com.shahad.app.my_school.ui.base.BaseBottomSheetDialogFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewClassFragment: BaseBottomSheetDialogFragment<FragmentDailogNewClassBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_dailog_new_class

    override val viewModel: NewClassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    private fun observe() {
        with(viewModel){
            onSuccessCreated.observeEvent(this@NewClassFragment){ ifSuccess ->
                takeIf { ifSuccess }?.let {
                    findNavController().navigateUp()
                }
            }
        }
    }


}