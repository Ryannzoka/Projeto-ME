package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "user_prefs"

private const val KEY_IS_LOGGED_IN = "is_logged_in"
private const val KEY_USERNAME = "username"
private const val KEY_VALIDATION_USERNAME = "validation_username"
private const val KEY_EMAIL = "email"
private const val KEY_PASSWORD = "password"

object UserDataStore {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(username: String, validationUsername: String, email: String, password: String) {

        with(sharedPreferences.edit()) {
            putString(KEY_USERNAME, username)
            putString(KEY_VALIDATION_USERNAME, validationUsername)
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    // FUNÇÃO SIMPLIFICADA: Altera apenas o estado de login
    fun setLoggedIn(isLoggedIn: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    // Retorna o estado de login
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Retorna o nome de usuário salvo (com a capitalização original)
    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    // Retorna o nome de usuário para validação (em minúsculas)
    fun getValidationUsername(): String? {
        return sharedPreferences.getString(KEY_VALIDATION_USERNAME, null)
    }

    // Retorna o e-mail salvo
    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    // Retorna a senha salva
    fun getPassword(): String? {
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }

    // Limpa apenas o estado de login, mantendo os dados do usuário.
    fun clearLoginState() {
        with(sharedPreferences.edit()) {
            remove(KEY_IS_LOGGED_IN)
            apply()
        }
    }
}
