package com.example.classify

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
  private const val BASE_URL = "https://your.api.url/" // Replace with your API base URL

  val instance: ApiService by lazy {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    retrofit.create(ApiService::class.java)
  }
}
