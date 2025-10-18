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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                loginError = false
            },
            label = { Text("Email ou Usuário") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                loginError = false
            },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val trimmedAndLowercasedEmailOrUsername = email.trim().lowercase()
                val userJson = UserDataStore.getUser(trimmedAndLowercasedEmailOrUsername)

                val isLoginSuccessful = userJson != null && userJson.getString("password") == password

                if (isLoginSuccessful) {
                    val validationUsername = userJson!!.getString("validationUsername")
                    UserDataStore.setLoggedInUser(validationUsername)
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