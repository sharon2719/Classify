package com.example.classify

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

  private lateinit var drawingView: DrawingView
  private lateinit var clearButton: Button
  private lateinit var classifyButton: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize UI elements
    drawingView = findViewById(R.id.drawingView)
    clearButton = findViewById(R.id.clearButton)
    classifyButton = findViewById(R.id.classifyButton)

    // Set up button listeners
    clearButton.setOnClickListener {
      drawingView.clearCanvas()
    }

    classifyButton.setOnClickListener {
      val bitmap = drawingView.getBitmap()
      classifyDigit(bitmap)
    }

    // Optionally call fetchApiData if you want to fetch data on app start
    fetchApiData()
  }

  private fun fetchApiData() {
    val call = RetrofitClient.instance.getData()
    call.enqueue(object : Callback<ApiResponse> {
      override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
        if (response.isSuccessful) {
          val data = response.body()
          // Use the data
          Toast.makeText(this@MainActivity, "Data: ${data?.name}", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(this@MainActivity, "Failed to get data", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
        Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
      }
    })
  }

  private fun sendApiData() {
    val requestData = RequestData(name = "example", value = "123")
    val call = RetrofitClient.instance.postData(requestData)

    call.enqueue(object : Callback<ApiResponse> {
      override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
        if (response.isSuccessful) {
          val responseData = response.body()
          Toast.makeText(this@MainActivity, "Posted data: ${responseData?.name}", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(this@MainActivity, "Failed to post data", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
        Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
      }
    })
  }

  private fun classifyDigit(bitmap: Bitmap) {
    // Convert bitmap to Base64 for easy transfer
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()
    val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

    // Prepare data for API request
    val requestData = ImageRequest(image = encodedImage)
    val call = RetrofitClient.instance.classifyDigit(requestData)

    call.enqueue(object : Callback<PredictionResponse> {
      override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
        if (response.isSuccessful) {
          val prediction = response.body()
          val predictedValue = prediction?.value
          val confidenceScore = prediction?.confidence

          Toast.makeText(this@MainActivity, "Prediction: $predictedValue with confidence score of: ${confidenceScore}%", Toast.LENGTH_LONG).show()
        } else {
          Toast.makeText(this@MainActivity, "Failed to classify digit", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
        Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
      }
    })
  }
}
