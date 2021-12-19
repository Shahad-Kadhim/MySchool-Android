package com.shahad.app.my_school.ui.book

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentBookBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment: BaseFragment<FragmentBookBinding>() {

    override fun getLayoutId() = R.layout.fragment_book
    override val viewModel: BookViewModel by viewModels()

}