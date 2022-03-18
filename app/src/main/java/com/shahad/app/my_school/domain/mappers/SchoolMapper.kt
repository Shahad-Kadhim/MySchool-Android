package com.shahad.app.my_school.domain.mappers

import com.shahad.app.my_school.data.local.entities.SchoolsEntity
import com.shahad.app.my_school.domain.models.School
import javax.inject.Inject

class SchoolMapper @Inject constructor(): BaseMapper<SchoolsEntity, School> {
    override fun map(input: SchoolsEntity): School =
        School(
            input.id,
            input.name
        )

}