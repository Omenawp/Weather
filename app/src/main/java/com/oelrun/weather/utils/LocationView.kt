package com.oelrun.weather.utils

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.oelrun.weather.R
import android.graphics.Paint

class LocationView@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    var position = 0
    var size = 0

    private lateinit var painSelect: Paint
    private lateinit var painMain: Paint

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        painMain = Paint().apply { color = ContextCompat.getColor(context, R.color.white_tr) }
        painSelect = Paint().apply { color = ContextCompat.getColor(context, R.color.white) }
    }

    override fun onDraw(canvas: Canvas) {
        val mid = size.toFloat() / 2 * 15f
        val start = width.toFloat() / 2 - mid
        val h = (height / 2).toFloat()

        for(i in 0 until size) {
            val x = start + 20f * i
            canvas.drawCircle(x, h, 5f, if(position == i) painSelect else painMain )
        }
    }
}