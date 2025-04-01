package com.example.khelo.data.repository

import com.example.khelo.data.dao.UserDao
import com.example.khelo.data.entities.User
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

class UserRepository(private val userDao: UserDao) {
    
    suspend fun registerUser(phoneNumber: String, name: String, email: String, password: String): Result<Unit> {
        return try {
            // Check if phone number is already registered
            if (userDao.isPhoneNumberRegistered(phoneNumber)) {
                return Result.failure(Exception("Phone number already registered"))
            }
            
            // Check if email is already registered
            if (userDao.isEmailRegistered(email)) {
                return Result.failure(Exception("Email already registered"))
            }
            
            // Hash the password
            val passwordHash = hashPassword(password)
            
            // Create user entity
            val user = User(
                phoneNumber = phoneNumber,
                name = name,
                email = email,
                passwordHash = passwordHash
            )
            
            // Insert user into database
            userDao.insertUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun loginUser(phoneNumber: String, password: String): Result<User> {
        return try {
            val user = userDao.getUserByPhone(phoneNumber)
                ?: return Result.failure(Exception("User not found"))
            
            val passwordHash = hashPassword(password)
            
            if (user.passwordHash != passwordHash) {
                return Result.failure(Exception("Incorrect password"))
            }
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserByPhone(phoneNumber: String): User? {
        return userDao.getUserByPhone(phoneNumber)
    }
    
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
    
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
