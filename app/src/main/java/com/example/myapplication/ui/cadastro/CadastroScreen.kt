package com.example.myapplication.ui.cadastro

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.UserDataStore
import java.util.regex.Pattern

private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

@Composable
fun CadastroScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val isEmailFormatValid = EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    val isPasswordLengthValid = password.length >= 6

    val isCadastroEnabled = username.isNotBlank() && isEmailFormatValid && isPasswordLengthValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Criar Nova Conta",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = null
            },
            label = { Text("Nome de Usuário") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("E-mail") },
            singleLine = true,
            isError = email.isNotBlank() && !isEmailFormatValid,
            modifier = Modifier.fillMaxWidth()
        )

        if (email.isNotBlank() && !isEmailFormatValid) {
            Text(
                text = "Formato de e-mail inválido.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Senha (mínimo 6 caracteres)") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = password.isNotBlank() && !isPasswordLengthValid,
            modifier = Modifier.fillMaxWidth()
        )

        if (password.isNotBlank() && !isPasswordLengthValid) {
            Text(
                text = "A senha deve ter no mínimo 6 caracteres.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                val trimmedUsername = username.trim()
                val trimmedAndLowercasedUsername = trimmedUsername.lowercase()
                val trimmedEmail = email.trim()

                if (UserDataStore.userExists(validationUsername = trimmedAndLowercasedUsername)) {
                    errorMessage = "Este nome de usuário já está em uso."
                } else if (UserDataStore.userExists(email = trimmedEmail)) {
                    errorMessage = "Este e-mail já está em uso."
                } else {
                    UserDataStore.saveUserData(
                        username = trimmedUsername,
                        validationUsername = trimmedAndLowercasedUsername,
                        email = trimmedEmail,
                        password = password
                    )

                    Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A1C81),
                contentColor = Color.White
            ),
            enabled = isCadastroEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Cadastro")
        }
    }
}