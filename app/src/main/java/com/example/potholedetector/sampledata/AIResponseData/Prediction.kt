package com.example.potholedetector.sampledata.AIResponseData

data class Prediction(
    val `class`: String,
    val class_id: Int,
    val confidence: Double,
    val detection_id: String,
    val height: Double,
    val width: Double,
    val x: Double,
    val y: Double
)