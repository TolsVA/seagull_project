package com.example.seagull_project.repository

import com.example.seagull_project.RetrofitClient
import com.example.seagull_project.Student
import com.example.seagull_project.data.model.StudentDTO

class StudentRepository {
    private val retrofit = RetrofitClient.apiService

    suspend fun saveStudent(student: StudentDTO): String {
        val response = retrofit.saveStudent(student)

        return if (response.isSuccessful) {
            response.body()?.string() ?: "Ошибка: пустой ответ от сервера"
        } else {
            "Ошибка: ${response.message()}"
        }
    }

    suspend fun getStudentByEmail(email: String): Student? {
        val response = retrofit.getStudentByEmail(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun deleteStudent(email: String): String {
        val response = retrofit.deleteStudent(email)
        return if (response.isSuccessful) {
            "Студент удалён"
        } else {
            "Ошибка: ${response.message()}"
        }
    }

    suspend fun updateStudent(student: StudentDTO): String {
        val response = retrofit.updateStudent(student)
        return if (response.isSuccessful) {
            "Студент успешно обновлён"
        } else {
            "Ошибка: ${response.message()}"
        }
    }
}

