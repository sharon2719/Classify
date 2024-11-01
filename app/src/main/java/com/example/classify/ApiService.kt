package com.example.classify

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
  @POST("classify") // Update with your endpoint
  fun classifyDigit(@Body request: ImageRequest): Call<PredictionResponse>

  @POST("data") // Update with your endpoint
  fun postData(@Body request: RequestData): Call<ApiResponse>

  @GET("data") // Update with your endpoint
  fun getData(): Call<ApiResponse>
}
