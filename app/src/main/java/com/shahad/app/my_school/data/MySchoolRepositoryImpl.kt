package com.shahad.app.my_school.data

import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.remote.MySchoolService
import javax.inject.Inject

class MySchoolRepositoryImpl @Inject constructor(
    dao: MySchoolDao,
    apiService: MySchoolService,
): MySchoolRepository