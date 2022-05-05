package com.shahad.app.my_school.ui.home.manger

import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.domain.models.School

sealed class HomeMangerItem(val rank: Int){
    class SchoolsItems(var list: List<School>): HomeMangerItem(1)
    object Nav : HomeMangerItem(2)
    class Classes(var list: List<ClassM>): HomeMangerItem(3)
}