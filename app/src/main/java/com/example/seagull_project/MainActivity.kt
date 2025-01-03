package com.example.seagull_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.seagull_project.repository.StudentRepository
import com.example.seagull_project.ui.AddStudentForm
import com.example.seagull_project.viewmodel.StudentViewModel

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography


class MainActivity : ComponentActivity() {
    private val studentRepository = StudentRepository() // Репозиторий
    private val studentViewModel = StudentViewModel(studentRepository) // ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeagullProjectTheme {
                AddStudentForm(viewModel = studentViewModel)
            }
        }
    }
}

// Создаем тему для нашего приложения
@Composable
fun SeagullProjectTheme(content: @Composable () -> Unit) {
    // Мы используем стандартную светлую цветовую схему и настроенную типографику
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5)
        ),
        typography = Typography(),
        content = content
    )
}