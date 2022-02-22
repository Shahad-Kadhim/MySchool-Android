package com.shahad.app.my_school.ui.classScreen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shahad.app.my_school.ui.members.MemberFragment
import com.shahad.app.my_school.ui.posts.PostFragment

class ClassPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){

     val fragments = listOf(PostFragment(),MemberFragment())

    override fun getItemCount(): Int  =fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}