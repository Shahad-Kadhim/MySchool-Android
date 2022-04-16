package com.shahad.app.my_school.data.remote.response

data class DutySubmit(
    val studentId: String,
    val dutyId: String,
    val submitDate: Long?,
    val solutionLink: String,
    val studentName: String
)