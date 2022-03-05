package com.shahad.app.my_school.domain.mappers

import com.shahad.app.my_school.data.remote.response.UserDto
import javax.inject.Inject

class UserInfoMapper @Inject constructor(): BaseMapper<UserDto,UserSelected> {
    override fun map(input: UserDto): UserSelected =
        UserSelected(
            input.id,
            input.name
        )

}