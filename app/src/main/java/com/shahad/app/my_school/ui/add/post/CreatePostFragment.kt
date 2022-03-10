package com.shahad.app.my_school.ui.add.post

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentCreatePostBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment: BaseFragment<FragmentCreatePostBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_create_post

    override val viewModel: CreatePostViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        observe()
    }

    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@CreatePostFragment){
                findNavController().navigateUp()
            }
        }
    }


}