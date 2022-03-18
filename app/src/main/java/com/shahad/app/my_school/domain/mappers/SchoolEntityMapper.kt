package com.shahad.app.my_school.domain.mappers

import com.shahad.app.my_school.data.local.entities.SchoolsEntity
import com.shahad.app.my_school.data.remote.response.SchoolDto
import javax.inject.Inject

class SchoolEntityMapper @Inject constructor(): BaseMapper<SchoolDto, SchoolsEntity> {
    override fun map(input: SchoolDto): SchoolsEntity =
        SchoolsEntity(
            input.id,
            input.name
        )

}