package com.shahad.app.my_school.ui.notification

import androidx.fragment.app.viewModels
import com.shahad.app.my_school.R
import com.shahad.app.my_school.databinding.FragmentNotificationBinding
import com.shahad.app.my_school.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment: BaseFragment<FragmentNotificationBinding>() {

    override fun getLayoutId() = R.layout.fragment_notification
    override val viewModel: NotificationViewModel by viewModels()


}