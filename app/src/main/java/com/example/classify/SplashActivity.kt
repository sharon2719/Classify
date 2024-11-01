package com.example.classify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

  private val splashDuration: Long = 3000 // Duration for the splash screen (in milliseconds)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash) // Ensure this points to your XML layout file

    // Find the ImageView
    val animatedImage: ImageView = findViewById(R.id.splash_image)

    // Load the animation and start it
    val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
    animatedImage.startAnimation(bounceAnimation)

    // Use a Handler to delay moving to MainActivity
    Handler().postDelayed({
      // Start the MainActivity
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)

      // Finish SplashActivity so it's removed from the back stack
      finish()
    }, splashDuration)
  }
}