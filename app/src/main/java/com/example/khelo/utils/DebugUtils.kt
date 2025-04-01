package com.example.khelo.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.khelo.data.model.User
import com.example.khelo.data.storage.LocalStorage
import com.example.khelo.utils.SecurityUtils

/**
 * Utility class for debugging purposes
 */
object DebugUtils {
    
    private const val TAG = "DebugUtils"
    
    /**
     * Prints information about all users in the local storage
     */
    fun debugPrintUsers(context: Context) {
        val localStorage = LocalStorage.getInstance(context)
        val users = localStorage.getAllUsers() // We'll add this method to LocalStorage
        
        Log.d(TAG, "======= USERS DEBUG INFO =======")
        Log.d(TAG, "Total users: ${users.size}")
        
        users.forEachIndexed { index, user ->
            Log.d(TAG, "User $index: ${user.phoneNumber}, ${user.name}, ${user.email}")
        }
        
        val currentUser = localStorage.getCurrentUser()
        Log.d(TAG, "Current logged in user: ${currentUser?.phoneNumber ?: "None"}")
        Log.d(TAG, "======= END USERS DEBUG INFO =======")
    }
    
    /**
     * Creates a test user for debugging purposes
     */
    fun createTestUser(context: Context): Boolean {
        Log.d(TAG, "Creating test user")
        val localStorage = LocalStorage.getInstance(context)
        
        val testUser = User(
            phoneNumber = "1234567890",
            name = "Test User",
            email = "test@example.com",
            passwordHash = SecurityUtils.hashPassword("password123")
        )
        
        val result = localStorage.saveUser(testUser)
        Log.d(TAG, "Test user created: $result")
        
        // Show a toast message
        Toast.makeText(context, "Test user created: $result", Toast.LENGTH_SHORT).show()
        
        return result
    }
    
    /**
     * Attempts to log in with the test user
     */
    fun loginWithTestUser(context: Context): Boolean {
        Log.d(TAG, "Logging in with test user")
        val localStorage = LocalStorage.getInstance(context)
        
        // Pass raw password instead of hash
        val result = localStorage.loginUser("1234567890", "password123")
        Log.d(TAG, "Test user login: $result")
        
        // Show a toast message
        Toast.makeText(context, "Test user login: $result", Toast.LENGTH_SHORT).show()
        
        return result
    }
}
