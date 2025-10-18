package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

private const val PREFS_NAME = "user_prefs"
private const val KEY_IS_LOGGED_IN = "is_logged_in"
private const val KEYS_USERS_DATA = "users_data"
private const val KEY_CURRENT_USER = "current_user"

object UserDataStore {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(username: String, validationUsername: String, email: String, password: String) {
        val jsonString = sharedPreferences.getString(KEYS_USERS_DATA, "{}")
        val usersJson = JSONObject(jsonString ?: "{}")

        val userJson = JSONObject().apply {
            put("username", username)
            put("validationUsername", validationUsername)
            put("email", email)
            put("password", password)
        }

        usersJson.put(validationUsername, userJson)

        with(sharedPreferences.edit()) {
            putString(KEYS_USERS_DATA, usersJson.toString())
            apply()
        }
    }

    fun getUser(validationUsernameOrEmail: String): JSONObject? {
        val jsonString = sharedPreferences.getString(KEYS_USERS_DATA, "{}")
        val usersJson = JSONObject(jsonString ?: "{}")

        val keys = usersJson.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val userJson = usersJson.getJSONObject(key)
            val email = userJson.getString("email")
            val validationUsername = userJson.getString("validationUsername")

            if (validationUsernameOrEmail == email.lowercase() || validationUsernameOrEmail == validationUsername) {
                return userJson
            }
        }
        return null
    }

    fun userExists(validationUsername: String? = null, email: String? = null): Boolean {
        val jsonString = sharedPreferences.getString(KEYS_USERS_DATA, "{}")
        val usersJson = JSONObject(jsonString ?: "{}")

        val keys = usersJson.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val userJson = usersJson.getJSONObject(key)
            val userEmail = userJson.getString("email")
            val userValidationUsername = userJson.getString("validationUsername")

            if (validationUsername != null && userValidationUsername == validationUsername) {
                return true
            }
            if (email != null && userEmail == email) {
                return true
            }
        }
        return false
    }

    fun setLoggedInUser(validationUsername: String) {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_CURRENT_USER, validationUsername)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCurrentUser(): JSONObject? {
        val currentUserKey = sharedPreferences.getString(KEY_CURRENT_USER, null)
        if (currentUserKey == null) return null

        val jsonString = sharedPreferences.getString(KEYS_USERS_DATA, "{}")
        val usersJson = JSONObject(jsonString ?: "{}")

        return if (usersJson.has(currentUserKey)) {
            usersJson.getJSONObject(currentUserKey)
        } else {
            null
        }
    }

    fun getUsername(): String? {
        return getCurrentUser()?.optString("username")
    }

    fun getEmail(): String? {
        return getCurrentUser()?.optString("email")
    }

    fun clearLoginState() {
        with(sharedPreferences.edit()) {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_CURRENT_USER)
            apply()
        }
    }
}