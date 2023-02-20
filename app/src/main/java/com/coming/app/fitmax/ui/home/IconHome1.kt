package com.coming.app.fitmax.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.coming.app.fitmax.R
import com.coming.app.fitmax.camera.VisualizationUtils

class IconItem(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, null) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    protected override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val x = width
        val y = height

        val rectf =
            RectF((x / 2).toFloat()-50, (y / 2).toFloat()-50, (y / 2).toFloat()+50, (y / 2).toFloat()+50)
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        paint.color = Color.parseColor("#CD5C5C")
        val paintText1 = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            textSize = 50f
            textAlign = Paint.Align.CENTER

        }
        canvas?.drawArc(rectf, 0f, 360f, false, Paint().apply {
            color = resources.getColor(R.color.gray_e2)
            strokeWidth = 10f
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        })
        canvas?.drawArc(rectf, 270f, 150f, false, Paint().apply {
            color = resources.getColor(R.color.light_blue_A200)
            strokeWidth = 10f
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        })
//        canvas?.drawText(
//           "25%",
//            (x / 2).toFloat() ,
//            (y / 2).toFloat()+10,
//            paintText1
//        )
//        canvas!!.drawCircle((x / 2).toFloat(), (y / 2).toFloat(), radius.toFloat(), paint)

        //Draw component in here
    }
}