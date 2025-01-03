package com.example.seagull_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.seagull_project.data.model.StudentDTO
import com.example.seagull_project.repository.StudentRepository

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {
    fun saveStudent(student: StudentDTO, onResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = repository.saveStudent(student)
                onResult(result, false) // false = успех
            } catch (e: Exception) {
                onResult("Ошибка: ${e.message}", true) // true = ошибка
            }
        }
    }
}
