package com.shahad.app.my_school.data

import android.util.Log
import com.google.gson.JsonElement
import com.shahad.app.my_school.data.local.daos.MySchoolDao
import com.shahad.app.my_school.data.remote.AuthenticationResponse
import com.shahad.app.my_school.data.remote.MySchoolService
import com.shahad.app.my_school.data.remote.response.*
import com.shahad.app.my_school.domain.mappers.DomainMappers
import com.shahad.app.my_school.domain.mappers.LocalMappers
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.domain.models.ClassM
import com.shahad.app.my_school.domain.models.School
import com.shahad.app.my_school.util.State
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MySchoolRepositoryImpl @Inject constructor(
    private val dao: MySchoolDao,
    private val apiService: MySchoolService,
    private val domainMappers: DomainMappers,
    private val localMappers: LocalMappers
): MySchoolRepository {

    override fun addUser(
        role: String,
        registerBody: JsonElement
    ): Flow<State<BaseResponse<AuthenticationResponse>?>> =
        wrapWithFlow { apiService.addUser(role, registerBody) }

    override fun loginUser(loginBody: JsonElement): Flow<State<BaseResponse<AuthenticationResponse>?>> =
        wrapWithFlow { apiService.loginUser(loginBody) }

    override fun getTeacherClasses(searchKey: String?): Flow<List<ClassM>> =
        wrapperClass(
            dao.getCLasses(searchKey ?: ""),
            domainMappers.classMapper::map
        )

    override fun getTeacherSchools(): Flow<List<School>> =
        wrapperClass(
            dao.getSchools(),
            domainMappers.schoolMapper::map
        )

    override suspend fun refreshTeacherClasses(searchKey: String?) {
        refreshWrapper(apiService::getTeacherClasses, dao::addClasses)
        { body ->
            body?.data?.map { classDto ->
                localMappers.classEntityMapper.map(classDto)
            }
        }
    }

    override fun getMangerSchool(): Flow<List<School>> =
        wrapperClass(
            dao.getSchools(),
            domainMappers.schoolMapper::map
        )


    override fun getStudentSchools(): Flow<List<School>> =
        wrapperClass(
            dao.getSchools(),
            domainMappers.schoolMapper::map
        )

    override suspend fun refreshTeacherSchool() {
        refreshWrapper(apiService::getTeacherSchools, dao::addSchool)
        { body ->
            body?.data?.map { schoolDto ->
                localMappers.schoolEntityMapper.map(schoolDto)
            }
        }
    }

    override suspend fun refreshMangerSchool() {
        refreshWrapper(apiService::getMangerSchools, dao::addSchool)
        { body ->
            body?.data?.map { schoolDto ->
                localMappers.schoolEntityMapper.map(schoolDto)
            }
        }
    }

    override suspend fun refreshStudentSchool() {
        refreshWrapper(apiService::getStudentSchools, dao::addSchool)
        { body ->
            body?.data?.map { schoolDto ->
                localMappers.schoolEntityMapper.map(schoolDto)
            }
        }
    }

    override fun getMangerClasses(): Flow<List<ClassM>> =
        wrapperClass(
            dao.getCLasses(),
            domainMappers.classMapper::map
        )

    override suspend fun refreshMangerClasses() {
        refreshWrapper(apiService::getMangerClasses, dao::addClasses)
        { body ->
            body?.data?.map { classDto ->
                localMappers.classEntityMapper.map(classDto)
            }
        }
    }

    override fun createSchool(schoolName: String): Flow<State<BaseResponse<SchoolDto>?>> =
        wrapWithFlow { apiService.createSchool(schoolName) }

    override fun createClass(requestBody: JsonElement): Flow<State<BaseResponse<ClassDto2>?>> =
        wrapWithFlow { apiService.createClass(requestBody) }

    override fun addStudentToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.addStudentToSchool(requestBody) }

    override fun addTeacherToSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.addTeacherToSchool(requestBody) }

    @FlowPreview
    override fun getSchoolStudents(
        schoolId: String,
        searchKey: String?
    ): Flow<State<BaseResponse<List<UserSelected>>?>> =
        wrapper(
            wrapWithFlow { apiService.getSchoolStudent(schoolId, searchKey) } ,
            domainMappers.userInfoMapper::map
        )

    @FlowPreview
    override fun getSchoolTeachers(
        schoolId: String,
        searchKey: String?
    ): Flow<State<BaseResponse<List<UserSelected>>?>> =
        wrapper(wrapWithFlow { apiService.getSchoolTeachers(schoolId, searchKey) },domainMappers.userInfoMapper::map)

    @FlowPreview
    override fun getStudentsNotInClass(classId: String): Flow<State<BaseResponse<List<UserSelected>>?>> =
        wrapper(wrapWithFlow { apiService.getStudentsInSchoolNotInClass(classId) } ,domainMappers.userInfoMapper::map)

    override fun addStudentToClass(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.addStudentsToClass(requestBody) }

    override fun getMemberClass(classId: String): Flow<State<BaseResponse<List<UserSelected>>?>> =
        wrapper(wrapWithFlow { apiService.getClassMember(classId) } ,domainMappers.userInfoMapper::map)

    override fun createPost(parts: HashMap<String,RequestBody>): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.createPost(parts) }

    override fun getPostInClass(classId: String): Flow<State<BaseResponse<List<PostDto>>?>> =
        wrapWithFlow { apiService.getPostsInClass(classId) }

    override fun getStudentInfo(id: String?): Flow<State<BaseResponse<StudentDto?>?>> =
        wrapWithFlow { apiService.getStudentInfo(id) }

    override fun getTeacherInfo(id: String?): Flow<State<BaseResponse<TeacherDto?>?>> =
        wrapWithFlow { apiService.getTeacherInfo(id) }

    override fun getMangerInfo(): Flow<State<BaseResponse<MangerInfoDto?>?>> =
        wrapWithFlow { apiService.getMangerInfo() }

    override fun removeStudentFromClass(requestBody: JsonElement): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.removeMemberFromClass(requestBody) }

    override fun removeStudentFromSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>  =
        wrapWithFlow { apiService.removeStudentFromSchool(requestBody) }

    override fun removeTeacherFromSchool(requestBody: JsonElement): Flow<State<BaseResponse<String>?>>  =
        wrapWithFlow { apiService.removeTeacherFromSchool(requestBody) }


    override fun deleteSchool(schoolId: String): Flow<State<BaseResponse<String>?>> =
        wrapWithFlow { apiService.deleteSchool(schoolId) }

    private fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<State<T?>> {
        return flow {
            emit(State.Loading)
            try {
                emit(checkIsSuccessful(function()))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
    }

    private fun <T> checkIsSuccessful(response: Response<T>): State<T?> =
        when {
            response.isSuccessful -> {
                State.Success(response.body())
            }
            response.code() == 401 -> {
                State.UnAuthorization
            }
            else -> {
                State.Error(response.message())
            }
        }

    @FlowPreview
    private fun <T, U> wrapper(
        data: Flow<State<BaseResponse<List<T>>?>>,
        mapper: (T) -> U
    ): Flow<State<BaseResponse<List<U>>?>> =
        data.map {
            when (it) {
                is State.Error, State.UnAuthorization, State.Loading -> it as State<BaseResponse<List<U>>?>
                is State.Success -> {
                    State.Success(
                        BaseResponse(it.data!!.statusCode, it.data!!.data.map { mapper(it) })
                    )
                }
            }
        }

    private fun <T, U> wrapperClass(
        data: Flow<List<T>>,
        mapper:( (T) -> U),
    ): Flow<List<U>> =
        data.map { list ->
            list.map { entity ->
                mapper(entity)
            }
        }


    private suspend fun <T, U> refreshWrapper(
        request: suspend () -> Response<T>,
        insertIntoDatabase: suspend (List<U>) -> Unit,
        mapper: (T?) -> List<U>?,
    ) {
        try {
            request().also {
                if (it.isSuccessful) {
                    mapper(it.body())?.let { list ->
                        insertIntoDatabase(list)
                    }
                }
            }
        } catch (exception: Exception) {
            Log.i("MY_SCHOOL", "no connection cant update data")
        }
    }


}