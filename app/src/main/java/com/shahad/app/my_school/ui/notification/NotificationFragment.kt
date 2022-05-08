package com.shahad.app.my_school.ui.notification

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentNotificationBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import com.shahad.app.my_school.ui.main.MainActivity
import com.shahad.app.my_school.util.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment: BaseFragment<FragmentNotificationBinding>() {

    override fun getLayoutId() = R.layout.fragment_notification
    override val viewModel: NotificationViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        observe()
        viewDataBinding.dutyRecycle.adapter = NotificationAdapterRecycler(listOf(),viewModel)
    }


    private fun observe() {
        with(viewModel){
            clickBackEvent.observeEvent(this@NotificationFragment){
                findNavController().navigateUp()
            }
            unAuthentication.observe(this@NotificationFragment){
                (requireActivity() as MainActivity).navToIdentity()
            }
        }
    }

}