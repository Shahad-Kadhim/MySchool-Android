package com.shahad.app.my_school.ui.home.student

import com.shahad.app.my_school.domain.models.ClassM

sealed class HomeItem{
    class ClassItem(var classes: List<ClassM>) : HomeItem()
    class DutyItem(val numberOfDuty: Int, val dutyComplete: Int): HomeItem()
}
