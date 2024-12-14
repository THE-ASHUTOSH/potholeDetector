package com.example.potholedetector

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.potholedetector.sampledata.LoginRequest
import com.example.potholedetector.sampledata.LoginResponse
import com.example.potholedetector.sampledata.SignUpRequest
import com.example.potholedetector.sampledata.SignUpResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.math.log

interface RegistrationApi {
    @POST("/api/users/register")
    suspend fun registerUser(@Body request: SignUpRequest): SignUpResponse

    @POST("/api/users/signin")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://potholedetectorreact-native-production.up.railway.app"

    val api: RegistrationApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegistrationApi::class.java)
    }
}

@Composable
fun getToken(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()):String{
    return viewModel.LoginState?.token ?: "TokenNotFound"
}

class RegistrationViewModel : ViewModel() {
    var registrationState by mutableStateOf<SignUpResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun registerUser(request: SignUpRequest) {
        viewModelScope.launch {
            Log.d("message","viewModelLaunch")
            isLoading = true
            try {
                Log.d("message","try")
                val response = RetrofitClient.api.registerUser(request)
                registrationState = response
                Log.d("response", registrationState?.message.toString())

            } catch (e: Exception) {
                Log.d("message","catch")
                errorMessage = e.message
                errorMessage?.let { Log.d(" bjhbkjkj", it)
                Log.d("errorrrrr",registrationState.toString())}
            } finally {
                Log.d("statussssss","working fine")
                isLoading = false
            }
        }
    }
}
// Login ViewModel
class LoginViewModel : ViewModel() {
    var LoginState by mutableStateOf<LoginResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loginUser(request: LoginRequest) {
        viewModelScope.launch {
            Log.d("message","viewModelLaunch")
            isLoading = true
            try {
                val response = RetrofitClient.api.loginUser(request)
                LoginState = response
                Log.d("response", LoginState?.message.toString())

            } catch (e: Exception) {
                Log.d("message","catch")
                errorMessage = e.message
                errorMessage?.let { Log.d(" catch", it)
                }
            } finally {
                isLoading = false
            }
        }
    }
}