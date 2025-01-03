package com.example.seagull_project

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope // Для использования lifecycleScope
import com.example.seagull_project.data.model.StudentDTO
import kotlinx.coroutines.launch // Для запуска корутин
import retrofit2.HttpException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeagullProjectTheme {
                AddStudentForm(addStudentToServer = { student ->
                    // Внутри активности вызываем addStudentToServer через lifecycleScope
                    lifecycleScope.launch {
                        addStudentToServer(student)
                    }
                })
            }
        }
    }

    // Функция для отправки данных на сервер, будет вызываться в активности
    private suspend fun addStudentToServer(student: StudentDTO) {
        // Убедимся, что все поля заполнены
        if (student.firstName.isEmpty() || student.lastName.isEmpty() || student.email.isEmpty() || student.dateOfBirth.isEmpty()) {
            return // Можно добавить обработку ошибки, если нужно
        }

        try {
            // Отправка запроса на сервер
            RetrofitClient.apiService.addStudent(student)
            Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: HttpException) {
            Toast.makeText(this, "Error: ${e.message()}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Unknown error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun AddStudentForm(addStudentToServer: (StudentDTO) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Функция для вызова запроса на сервер
    fun handleAddStudent() {
        // Убедимся, что все поля заполнены
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dateOfBirth.isEmpty()) {
            errorMessage = "All fields are required!"
            return
        }

        // Создаем объект запроса
        val newStudent = StudentDTO(firstName, lastName, dateOfBirth, email)

        // Вызов функции для добавления студента на сервер
        addStudentToServer(newStudent)

        // Очистка данных после отправки
        firstName = ""
        lastName = ""
        email = ""
        dateOfBirth = ""
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") }
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth (YYYY-MM-DD)") },
        )

        // Показать сообщение об ошибке, если оно есть
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(onClick = { handleAddStudent() }) {
            Text("Add Student")
        }

        // Показываем индикатор загрузки, если запрос в процессе
        if (isLoading) {
            Text(text = "Loading...", color = androidx.compose.ui.graphics.Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SeagullProjectTheme {
        AddStudentForm(addStudentToServer = { }) // Передаем пустую функцию для Preview
    }
}

@Composable
fun SeagullProjectTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}