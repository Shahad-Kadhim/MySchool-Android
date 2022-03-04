package com.shahad.app.my_school.ui.classScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentClassScreenBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.members.MemberFragment
import com.shahad.app.my_school.ui.posts.PostFragment
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassScreenFragment: BaseFragment<FragmentClassScreenBinding>() {

    override fun getLayoutId() = R.layout.fragment_class_screen
    override val viewModel: ClassScreenViewModel by viewModels()

    private val args: ClassScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf<Fragment>(
            PostFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ROLE",args.isAuth)
                    putString("ID",args.id)
                }
            } ,
            MemberFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ROLE",args.isAuth)
                    putString("ID",args.id)
                }
            }
        )

        viewDataBinding.pager.adapter =ClassPagerAdapter(this,fragments)
        viewDataBinding.className= args.className

        observe()
    }

    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@ClassScreenFragment){
                findNavController().navigateUp()
            }
        }
    }

}