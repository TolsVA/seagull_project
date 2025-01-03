package com.example.seagull_project

import com.example.seagull_project.data.model.StudentDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/students")
    suspend fun getStudents(): List<Student>

    // Добавление нового студента
    @POST("api/v1/students/save_student")
    suspend fun addStudent(@Body student: StudentDTO): Student

    // Обновление студента
    @PUT("api/v1/studentsId/delete_student/{id}")
    suspend fun updateStudent(@Path("id") id: Int, @Body student: Student): Student

    // Удаление студента
    @DELETE("api/v1/studentsId/delete_student/{id}")
    suspend fun deleteStudent(@Path("id") id: Int): Unit
}
