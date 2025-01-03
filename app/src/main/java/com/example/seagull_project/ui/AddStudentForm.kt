package com.example.seagull_project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.seagull_project.data.model.StudentDTO
import com.example.seagull_project.ui.viewmodel.StudentViewModel

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Student Management App", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("add_student") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Student")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("students_list") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Students")
        }
    }
}

@Composable
fun AddStudentScreen(navController: NavController, viewModel: StudentViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        // Кнопка "Add Student"
        Button(
            onClick = {
                // Создаем нового студента
                val student = StudentDTO(firstName, lastName, email, dateOfBirth)

                // Вызываем метод для сохранения студента
                viewModel.saveStudent(student) { responseMessage, error ->
                    message = responseMessage
                    isError = error
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Student")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Показать сообщение после добавления студента
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Back"
        Button(
            onClick = { navController.popBackStack() },  // Go back to home screen
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
        }
    }
}

@Composable
fun StudentsListScreen(navController: NavController, viewModel: StudentViewModel) {
    // Загружаем студентов с сервера при старте экрана
    LaunchedEffect(Unit) {
        viewModel.loadStudents()
    }

    // Получаем список студентов из ViewModel
    val students by viewModel.students

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("List of Students", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Если список студентов пуст, показываем сообщение
        if (students.isEmpty()) {
            Text("No students found.", style = MaterialTheme.typography.bodyLarge)
        } else {
            // Иначе отображаем список студентов
            students.forEach { student ->
                Text(student.firstName + " " + student.lastName, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Back"
        Button(
            onClick = { navController.popBackStack() },  // Go back to home screen
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
        }
    }
}