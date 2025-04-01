package com.example.khelo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.util.Log
import com.example.khelo.data.model.Player
import com.example.khelo.data.model.User
import com.example.khelo.data.storage.LocalStorage
import com.example.khelo.ui.theme.KheloTheme
import com.example.khelo.ui.theme.PrimaryGreen
import com.example.khelo.ui.theme.TextPrimary
import com.example.khelo.utils.DebugUtils
import com.example.khelo.utils.SecurityUtils
import com.example.khelo.HomeScreen
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isRegistering by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val localStorage = LocalStorage.getInstance(context)
    
    // Check if already logged in
    LaunchedEffect(Unit) {
        Log.d("RegistrationScreen", "Checking if already logged in")
        if (localStorage.isLoggedIn()) {
            Log.d("RegistrationScreen", "User is already logged in, navigating to home")
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        
        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        // Phone Number Field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                cursorColor = TextPrimary
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
        
        // Error Message
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        // Register Button
        Button(
            onClick = {
                // Validate inputs
                if (name.isBlank()) {
                    errorMessage = "Please enter your name"
                    return@Button
                }
                
                if (phoneNumber.isBlank()) {
                    errorMessage = "Please enter your phone number"
                    return@Button
                }
                
                if (email.isBlank()) {
                    errorMessage = "Please enter your email"
                    return@Button
                }
                
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Please enter a valid email address"
                    return@Button
                }
                
                if (password.isBlank()) {
                    errorMessage = "Please enter your password"
                    return@Button
                }
                
                if (password.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                    return@Button
                }
                
                if (confirmPassword.isBlank()) {
                    errorMessage = "Please confirm your password"
                    return@Button
                }
                
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                    return@Button
                }
                
                // Proceed with registration
                isRegistering = true
                errorMessage = null
                
                Log.d("RegistrationScreen", "Creating user: $phoneNumber, $name, $email")
                
                try {
                    // Create user
                    val passwordHash = SecurityUtils.hashPassword(password)
                    Log.d("RegistrationScreen", "Password hashed successfully")
                    
                    val user = User(
                        phoneNumber = phoneNumber,
                        name = name,
                        email = email,
                        passwordHash = passwordHash
                    )
                    Log.d("RegistrationScreen", "User object created successfully")
                    
                    // Save user
                    val success = localStorage.saveUser(user)
                    Log.d("RegistrationScreen", "User saved: $success")
                    
                    if (success) {
                        // Create player profile
                        try {
                            val player = Player(
                                id = UUID.randomUUID().toString(),
                                userPhone = phoneNumber
                            )
                            Log.d("RegistrationScreen", "Player object created successfully")
                            
                            val playerSaved = localStorage.savePlayer(player)
                            Log.d("RegistrationScreen", "Player saved: $playerSaved")
                            
                            // Login user with raw password
                            val loginSuccess = localStorage.loginUser(phoneNumber, password)
                            Log.d("RegistrationScreen", "User logged in: $loginSuccess")
                            
                            // Debug print users
                            DebugUtils.debugPrintUsers(context)
                            
                            // Navigate to home screen - use the correct route
                            navController.navigate(HomeScreen.route) {
                                popUpTo("login") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            Log.e("RegistrationScreen", "Error creating player profile", e)
                            errorMessage = "Registration failed: ${e.message}"
                        }
                    } else {
                        Log.e("RegistrationScreen", "Failed to save user")
                        errorMessage = "Failed to register. Please try again."
                    }
                } catch (e: Exception) {
                    Log.e("RegistrationScreen", "Error during registration", e)
                    errorMessage = "Registration error: ${e.message}"
                } finally {
                    isRegistering = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 8.dp),
            enabled = !isRegistering,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            if (isRegistering) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Register")
            }
        }
        
        // Login Link
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                color = TextPrimary
            )
            TextButton(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Login",
                    color = PrimaryGreen
                )
            }
        }
        
        // Debug options in development mode
        if (true) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    DebugUtils.debugPrintUsers(context)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Debug: Print Users")
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    KheloTheme {
        RegistrationScreen(navController = rememberNavController())
    }
}
