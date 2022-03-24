package com.shahad.app.my_school.domain.mappers

import com.shahad.app.my_school.data.local.entities.ClassEntity
import com.shahad.app.my_school.data.remote.response.ClassDto
import javax.inject.Inject

class ClassEntityMapper @Inject constructor(): BaseMapper<ClassDto, ClassEntity> {
    override fun map(input: ClassDto): ClassEntity =
        ClassEntity(
            id = input.id,
            name = input.name,
            teacherName = input.teacherName,
            stage = input.stage
        )
}