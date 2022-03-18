package com.shahad.app.my_school.domain.mappers

import javax.inject.Inject

class DomainMappers @Inject constructor(
    val userInfoMapper: UserInfoMapper,
    val schoolMapper: SchoolMapper
)