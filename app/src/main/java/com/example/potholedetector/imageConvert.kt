package com.example.potholedetector

import android.content.Context
import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun createMultipartBodyFromBitmap(context: Context, bitmap: Bitmap): MultipartBody.Part {
    // Create a temp file
    val file = File(context.cacheDir, "image.jpg")
    val outputStream = FileOutputStream(file)

    // Compress the Bitmap to JPEG format
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    // Create a RequestBody for the file
    val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)

    // Create the MultipartBody.Part
    return MultipartBody.Part.createFormData("file", file.name, requestFile)
}
