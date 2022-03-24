package com.shahad.app.my_school.domain.mappers

import javax.inject.Inject

class LocalMappers @Inject constructor(
    val schoolEntityMapper: SchoolEntityMapper,
    val classEntityMapper: ClassEntityMapper,
)