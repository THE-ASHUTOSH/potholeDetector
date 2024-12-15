package com.example.potholedetector

import androidx.compose.runtime.mutableStateOf
import com.example.potholedetector.sampledata.AIResponseData.AIResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException


interface AIApi {
    @Multipart
    @POST("1?api_key=m8tVNuOQJCwCLq5343N8")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): AIResponse
}

object RetrofitClient2 {
    private const val BASE_URL = "https://detect.roboflow.com/pothole-detection-in1d6/"

    val api: AIApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AIApi::class.java)
    }
}


class AIViewModel : ViewModel() {
    var uploadState by mutableStateOf<AIResponse?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun uploadImage(context: Context, bitmap: Bitmap) {
        viewModelScope.launch {
            isLoading = true
            try {
                val multipartBody = createMultipartBodyFromBitmap(context, bitmap)
                val response = RetrofitClient2.api.uploadImage(multipartBody)
                uploadState = response
                Log.d("tryBlock", "trying ${response}")

            } catch (e: HttpException) {
                errorMessage = e.response()?.errorBody()?.string() ?: e.message()
                Log.d("error HttpException", errorMessage.toString())
            } catch (e: Exception) {
                errorMessage = e.message
                Log.d("error Exception", errorMessage.toString())
            } finally {
                isLoading = false
            }
        }
    }
}

