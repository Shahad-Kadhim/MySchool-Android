package com.shahad.app.my_school.domain.mappers

import com.shahad.app.my_school.data.local.entities.ClassEntity
import com.shahad.app.my_school.domain.models.ClassM
import javax.inject.Inject

class ClassMapper @Inject constructor(): BaseMapper<ClassEntity, ClassM> {
    override fun map(input: ClassEntity): ClassM =
        ClassM(
            input.id,
            input.name,
            input.teacherName,
            input.stage
        )
}