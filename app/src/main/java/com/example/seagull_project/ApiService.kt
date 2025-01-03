package com.example.seagull_project

import com.example.seagull_project.data.model.StudentDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("api/v1/students")
    suspend fun getStudents(): Response<List<StudentDTO>>

    @POST("api/v1/students/save_student")
    suspend fun saveStudent(@Body student: StudentDTO): Response<ResponseBody>  // ResponseBody для простой строки

    @GET("api/v1/students/save_student/{email}")
    suspend fun getStudentByEmail(@Path("email") email: String): Response<Student>

    @PUT("api/v1/students/save_student/update_student")
    suspend fun updateStudent(@Body student: StudentDTO): Response<Student>

    @DELETE("api/v1/students/save_student/delete_student/{email}")
    suspend fun deleteStudent(@Path("email") email: String): Response<ResponseBody>
}
