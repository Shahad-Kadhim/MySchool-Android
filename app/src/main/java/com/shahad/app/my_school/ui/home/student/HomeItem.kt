package com.shahad.app.my_school.ui.home.student

import com.shahad.app.my_school.domain.models.ClassM

sealed class HomeItem(val rank: Int){
    class ClassItem(var classI: ClassM) : HomeItem(3)
    object ClassesLabelItem : HomeItem(2)
    class DutyItem(val statistics: String): HomeItem(1)
}
