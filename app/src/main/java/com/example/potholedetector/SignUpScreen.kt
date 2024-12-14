package com.example.potholedetector

import android.annotation.SuppressLint
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
import com.example.potholedetector.sampledata.SignUpRequest







@Composable
fun SignUpScreen(navController: NavController, viewModel: RegistrationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    var registrationState = viewModel.registrationState
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
            text = "SignUp",
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

        CustomTextField(label = "Name", value = name, onValueChange = {newName -> name = newName} )
        CustomTextField(label = "Email", value = email , onValueChange = {newemail -> email =
            newemail})
        CustomTextField(
            label = "Password",
            value = password,
            onValueChange = {newPass -> password = newPass},
            keyboardType = KeyboardType.Password,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text(text = "Registering...", color = Color.Gray)
        }else{
        Button(
            onClick = {
                val request = SignUpRequest(name, email, password)
                viewModel.registerUser(request)
                      Log.d("Click","Click")},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text(
                text = "SignUp",
                color = Color.White,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }}

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {navController.popBackStack()}) {
            Text(
                text = "Already Registered? Log in here.",
                color = Color.Gray
            )
        }




        registrationState?.let {
            Log.d("from screen", it.message)
            if (it.message =="User registered successfully") {
                Log.d("registered", "registered")
                Toast.makeText(context, "Registration Successful: ${it.message}", Toast.LENGTH_SHORT).show()
                navController.navigate("login")
            }
            else {
                Log.d("registered error", "registered error")
                Text(text = "Error: ${it.message}", color = Color.Red)
            }
        }
        errorMessage?.let {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
            Log.d("error","error")
        }
    }
}