package com.example.seagull_project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.seagull_project.data.model.StudentDTO
import com.example.seagull_project.data.repository.StudentRepository

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    private val _students = mutableStateOf<List<StudentDTO>>(emptyList())
    val students: State<List<StudentDTO>> = _students

    // Метод для сохранения студента
    fun saveStudent(student: StudentDTO, onResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Сохраняем студента через репозиторий
                val result = repository.saveStudent(student)
                onResult(result, false) // false = успех
            } catch (e: Exception) {
                onResult("Ошибка: ${e.message}", true) // true = ошибка
            }
        }
    }

    // Метод для загрузки списка студентов с сервера
    fun loadStudents() {
        viewModelScope.launch {
            try {
                // Получаем список студентов через репозиторий
                val loadedStudents = repository.getStudents()
                _students.value = loadedStudents // Обновляем состояние списка студентов
            } catch (e: Exception) {
                // В случае ошибки выводим пустой список или обрабатываем ошибку
                _students.value = emptyList()
            }
        }
    }
}

