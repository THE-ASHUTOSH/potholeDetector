package com.example.potholedetector.sampledata.AIResponseData

data class AIResponse(
    val image: Image,
    val inference_id: String,
    val predictions: List<Prediction>,
    val time: Double
)