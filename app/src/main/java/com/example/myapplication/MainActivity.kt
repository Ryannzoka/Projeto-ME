package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.UserDataStore
import com.example.myapplication.ui.splash.SplashScreen
import com.example.myapplication.ui.login.LoginScreen
import com.example.myapplication.ui.cadastro.CadastroScreen
import com.example.myapplication.ui.main.MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()


        UserDataStore.init(this)

        val startDestination = "splash_screen"

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = startDestination) {

                    composable("splash_screen") {
                        SplashScreen(onNavigateNext = {
                            if (UserDataStore.isLoggedIn()) {
                                navController.navigate("main_screen") {
                                    popUpTo("splash_screen") { inclusive = true }
                                }
                            } else {
                                navController.navigate("login_screen") {
                                    popUpTo("splash_screen") { inclusive = true }
                                }
                            }
                        })
                    }


                    composable("login_screen") {
                        LoginScreen(
                            onNavigateToMain = {
                                navController.navigate("main_screen") {
                                    popUpTo("login_screen") { inclusive = true }
                                }
                            },
                            onNavigateToCadastro = {
                                navController.navigate("cadastro_screen")
                            }
                        )
                    }

                    composable("cadastro_screen") {
                        CadastroScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("main_screen") {
                        MainScreen(
                            onLogout = {
                                UserDataStore.clearLoginState()
                                navController.navigate("login_screen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MyApplicationTheme {
        LoginScreen(
            onNavigateToMain = {},
            onNavigateToCadastro = {}
        )
    }
}