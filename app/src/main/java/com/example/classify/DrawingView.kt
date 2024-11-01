package com.example.classify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
  private var paint = Paint().apply {
    color = Color.BLACK
    isAntiAlias = true
    strokeWidth = 40f
    style = Paint.Style.STROKE
    strokeJoin = Paint.Join.ROUND
    strokeCap = Paint.Cap.ROUND
  }

  private var path = android.graphics.Path()
  private lateinit var bitmap: Bitmap
  private lateinit var canvas: Canvas

  init {
    // Set up the view’s paint and background color
    setBackgroundColor(Color.WHITE)
  }

  override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
    super.onSizeChanged(width, height, oldWidth, oldHeight)
    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    canvas = Canvas(bitmap)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawBitmap(bitmap, 0f, 0f, paint)
    canvas.drawPath(path, paint)
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    val x = event.x
    val y = event.y
    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        path.moveTo(x, y)
        return true
      }
      MotionEvent.ACTION_MOVE -> {
        path.lineTo(x, y)
      }
      MotionEvent.ACTION_UP -> {
        canvas.drawPath(path, paint)
        path.reset()
      }
    }
    invalidate()
    return true
  }

  fun clearCanvas() {
    // Clear the bitmap and refresh the canvas
    bitmap.eraseColor(Color.WHITE)
    invalidate()
  }

  fun getBitmap(): Bitmap {
    // Return the current bitmap for classification
    return bitmap
  }
}
