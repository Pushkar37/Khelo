package com.example.khelo.utils

import android.util.Log
import java.security.MessageDigest
import java.util.Base64

object SecurityUtils {
    
    private const val TAG = "SecurityUtils"
    private const val SALT = "KheloAppSalt123" // A simple salt for all passwords
    
    /**
     * Hashes a password using SHA-256 with a salt
     */
    fun hashPassword(password: String): String {
        Log.d(TAG, "Hashing password (length: ${password.length})")
        
        try {
            // Add salt to password
            val saltedPassword = password + SALT
            
            // Hash the salted password
            val bytes = MessageDigest.getInstance("SHA-256").digest(saltedPassword.toByteArray())
            val hashedPassword = bytes.joinToString("") { "%02x".format(it) }
            
            Log.d(TAG, "Password hashed successfully, hash length: ${hashedPassword.length}")
            return hashedPassword
        } catch (e: Exception) {
            Log.e(TAG, "Error hashing password", e)
            // Fallback to a simple hash if there's an error
            return password.hashCode().toString()
        }
    }
    
    /**
     * Verifies a password against a hash
     */
    fun verifyPassword(password: String, hash: String): Boolean {
        val calculatedHash = hashPassword(password)
        val result = calculatedHash == hash
        Log.d(TAG, "Password verification result: $result")
        return result
    }
}
