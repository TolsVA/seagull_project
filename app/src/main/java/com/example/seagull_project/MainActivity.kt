package com.example.seagull_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.example.seagull_project.repository.StudentRepository
import com.example.seagull_project.ui.AddStudentScreen
import com.example.seagull_project.ui.HomeScreen
import com.example.seagull_project.ui.StudentsListScreen
import com.example.seagull_project.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {
    private val studentRepository = StudentRepository()
    private val studentViewModel = StudentViewModel(studentRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeagullProjectTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable("add_student") { AddStudentScreen(navController, studentViewModel) }
                    composable("students_list") { StudentsListScreen(navController, studentViewModel) }
                }
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