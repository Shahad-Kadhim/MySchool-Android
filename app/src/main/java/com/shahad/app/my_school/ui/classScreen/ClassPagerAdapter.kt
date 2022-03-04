package com.shahad.app.my_school.ui.classScreen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClassPagerAdapter(
    fragment: Fragment,
    val fragments: List<Fragment>,
): FragmentStateAdapter(fragment){

    override fun getItemCount(): Int  =fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}