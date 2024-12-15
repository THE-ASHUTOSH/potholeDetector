package com.example.potholedetector

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.potholedetector.sampledata.LoginRequest
import com.example.potholedetector.sampledata.SignUpResponse
import kotlinx.coroutines.delay

var buttonClicked : Boolean = false
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading = viewModel.isLoading
    var errorMessage = viewModel.errorMessage
    val LoginState = viewModel.LoginState
    val context = LocalContext.current
    viewModel.email= email

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Text(
            text = "Login",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.car),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "RoadFix",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )

        Spacer(modifier = Modifier.height(24.dp))


        CustomTextField(
            label = "Email", value = email,
            onValueChange = { newemail ->
                email =
                    newemail
            },
        )
        CustomTextField(
            label = "Password",
            value = password,
            onValueChange = { newPass -> password = newPass },
            keyboardType = KeyboardType.Password,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text(text = "Logging In...", color = Color.Gray)
        } else {
            Button(
                onClick = {
                    val request = LoginRequest(email, password)
                    viewModel.loginUser(request)
                    buttonClicked = true
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Footer
        TextButton(onClick = { navController.navigate("signup") }) {
            Text(
                text = "Do not have a account? SignUp here",
                color = Color.Gray
            )
        }
        if (isLoading == false) {
            if(LoginState!=null) {
                LoginState?.let {
                    Log.d("from screen", it.message)
                    if (it.message == "Sign-in successful") {
                        Text(text = "User ${it.message}", color = Color.Green)
                        Log.d("token", it.token)
                        navController.navigate("cam")
                    } else {
                        Log.d("registered error", "registered error")
                        Text(text = "Error: ${it.message}", color = Color.Red)
                    }
                }
            }else {
                errorMessage?.let {
                    Text(text = "Invalid Email or Password", color = Color.Red)
                    Log.d("error", "error")
                    if (buttonClicked) {
                        Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT)
                            .show()
                        buttonClicked = false
                    }
                }
            }
        }
    }
}
