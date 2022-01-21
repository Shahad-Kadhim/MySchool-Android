package com.shahad.app.my_school.util

fun Boolean?.isTrue(): Boolean = this == true

fun Boolean?.isNullOrTrue(): Boolean = this != false

fun Boolean?.isFalse(): Boolean = this == false

fun Boolean?.isNullOrFalse(): Boolean = this != true