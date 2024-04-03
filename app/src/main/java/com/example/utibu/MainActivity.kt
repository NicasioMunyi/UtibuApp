package com.example.utibu

import UserService
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.utibu.ui.LoginScreen
import com.example.utibu.ui.theme.UtibuTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val userService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.43.2:3000") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UtibuTheme {
                LoginScreen(
//                    userService = userService,
//                    onLoginSuccess = { response ->
//                        // Navigate to the next screen or perform other actions
//                        val userId = response.user?.userId
//                        val username = response.user?.username
//
//                        if (userId != null && username != null) {
//                            // Login successful, navigate to medicatin screen
//                            val intent = Intent(this@MainActivity, MedicationActivity::class.java)
//                            intent.putExtra("userId", userId)
//                            intent.putExtra("username", username)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            // Handle missing user data
//                            Toast.makeText(
//                                this@MainActivity,
//                                "Invalid user data",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
                //)
//                    this was for testing without keeping loging in
                    onLoginSuccess = {
                        // Navigate to MedicationActivity
                        val intent = Intent(this@MainActivity, MedicationActivity::class.java)
                        startActivity(intent)
                        finish()
                    })
            }
        }
    }
}
