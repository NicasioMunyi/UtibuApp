package com.example.utibu.ui

import LoginRequest
import LoginResponse
import UserService
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(
//    userService: UserService,
//    onLoginSuccess: (LoginResponse) -> Unit
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome text
        Text(
            text = "Welcome to Utibu",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,

            modifier = Modifier.padding(bottom = 40.dp)
        )

        Text(
            text = "Enter Email and password to Login",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Blue,
            modifier = Modifier.padding(bottom = 10.dp)
        )


        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Login button
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
//                    if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
//                        login(email.text, password.text, userService, onLoginSuccess)
//                    } else {
//                        loginError = "Please enter email and password"
//                    }
                    onLoginSuccess();
                }
            ) {
                Text("Login")
            }
        }

        // Register text
        TextButton(
            onClick = {
                // Handle navigation to registration screen
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Register?")
        }

        // Error message
        if (loginError.isNotEmpty()) {
            Text(
                text = loginError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


private fun login(
    email: String,
    password: String,
    userService: UserService,
    onLoginSuccess: (LoginResponse) -> Unit
) {
    var loginError = ""
    var isLoading = false

    userService.login(LoginRequest(email, password)).enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null && loginResponse.status == "success") {
                    onLoginSuccess(loginResponse)
                } else {
                    // Handle invalid credentials or other errors
                    loginError = "Invalid email or password"
                }
            } else {
                // Handle HTTP error
                loginError = "HTTP Error: ${response.code()}"
            }
            isLoading = false
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            // Handle network failure
            loginError = "Network Failure: ${t.message}"
            isLoading = false
        }
    })
}

