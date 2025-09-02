package com.example.myapplication.ui.login

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.UserDataStore

@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToCadastro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

    // O Column é usado para organizar os elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título da tela
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo para o e-mail ou nome de usuário
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                loginError = false // Reseta o erro ao digitar
            },
            label = { Text("Email ou Usuário") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para a senha
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginError = false // Reseta o erro ao digitar
            },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Botão de login
        Button(
            onClick = {
                val trimmedAndLowercasedEmailOrUsername = email.trim().lowercase()

                val savedValidationUsername = UserDataStore.getValidationUsername()
                val savedEmail = UserDataStore.getEmail()
                val savedPassword = UserDataStore.getPassword()

                val isLoginSuccessful = (trimmedAndLowercasedEmailOrUsername == savedEmail || trimmedAndLowercasedEmailOrUsername == savedValidationUsername) && password == savedPassword

                if (isLoginSuccessful) {
                    UserDataStore.setLoggedIn(isLoggedIn = true)
                    onNavigateToMain()
                } else {
                    loginError = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A1C81),
                contentColor = Color.White
            ),
            enabled = isLoginEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        if (loginError) {
            Text(
                text = "E-mail ou senha incorretos.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Botão para navegar para a tela de cadastro
        Button(
            onClick = onNavigateToCadastro,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A1C81),
                contentColor = Color.White
            )
        ) {
            Text("Ainda não tenho uma conta")
        }
    }
}
