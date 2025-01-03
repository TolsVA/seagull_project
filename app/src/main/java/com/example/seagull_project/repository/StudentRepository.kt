package com.example.seagull_project.repository

import com.example.seagull_project.RetrofitClient
import com.example.seagull_project.Student
import com.example.seagull_project.data.model.StudentDTO

class StudentRepository {
    private val retrofit = RetrofitClient.apiService

    // Метод для сохранения студента
    suspend fun saveStudent(student: StudentDTO): String {
        val response = retrofit.saveStudent(student)
        return if (response.isSuccessful) {
            response.body()?.string() ?: "Ошибка: пустой ответ от сервера"
        } else {
            "Ошибка: ${response.message()}"
        }
    }

    // Метод для получения студента по email
    suspend fun getStudentByEmail(email: String): Student? {
        val response = retrofit.getStudentByEmail(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    // Метод для удаления студента по email
    suspend fun deleteStudent(email: String): String {
        val response = retrofit.deleteStudent(email)
        return if (response.isSuccessful) {
            "Студент удалён"
        } else {
            "Ошибка: ${response.message()}"
        }
    }

    // Метод для обновления студента
    suspend fun updateStudent(student: StudentDTO): String {
        val response = retrofit.updateStudent(student)
        return if (response.isSuccessful) {
            "Студент успешно обновлён"
        } else {
            "Ошибка: ${response.message()}"
        }
    }

    // Метод для получения списка студентов
    suspend fun getStudents(): List<StudentDTO> {
        val response = retrofit.getStudents()
        return if (response.isSuccessful) {
            response.body() ?: emptyList() // Возвращаем пустой список, если тело ответа пустое
        } else {
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }
}


