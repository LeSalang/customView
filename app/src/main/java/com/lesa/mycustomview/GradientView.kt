package com.lesa.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class GradientView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {
    var listener: Listener? = null
    private val paint = Paint()
    private val paintText = Paint()
    private val strokeWidth = 250f
    private val space = 1
    init {
        paint.color = Color.BLACK
        paint.strokeWidth = strokeWidth

        paintText.style = Paint.Style.FILL
        paintText.color = Color.WHITE
        paintText.textSize = 40f
    }



    private val colorList = createGradient(Argb10.createRandomArgb10(), Argb10.createRandomArgb10(), (3..15).random())
    private val sweepAngle = 360f / colorList.size
    private var buttonClicked = -1

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircleButton(canvas)
        drawnMenuText(canvas)
    }

    private fun drawCircleButton(canvas: Canvas) {
        val centerX = (width / 2f)
        val centerY = (height / 2f)
        val radius = (width.coerceAtMost(height) - strokeWidth) / 2f

        for (i in colorList.indices) {
            paint.color = if (i != buttonClicked) colorList[i % colorList.size] else Color.WHITE
            paint.color = colorList[i % colorList.size]
            paint.style = Paint.Style.STROKE
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                i * sweepAngle + space,
                sweepAngle - 2 * space,
                false,
                paint
            )
        }
        paint.style = Paint.Style.FILL
        paint.color = if (buttonClicked>=0) colorList[buttonClicked] else Color.WHITE
        canvas.drawCircle(
            centerX,
            centerY,
            (radius - strokeWidth / 2f) - 8 * space,
            paint
        )
    }

    private fun drawnMenuText(canvas: Canvas) {
        colorList.forEachIndexed { index, i ->
            val string = i.toString()
            val rect = Rect()
            val angle = (index + 0.5f) * sweepAngle
            val coordinate = getTextCoordinates(angle)
            if (angle in 91f..<270f) {
                canvas.rotate(angle + 180f, coordinate.first, coordinate.second)
            } else {
                canvas.rotate(angle, coordinate.first, coordinate.second)
            }

            paintText.getTextBounds(string, 0, string.length, rect)
            canvas.drawText(
                string,
                coordinate.first - rect.exactCenterX(),
                coordinate.second - rect.exactCenterY(),
                paintText
            )
            if (angle in 91f..<270f) {
                canvas.rotate(-angle - 180f, coordinate.first, coordinate.second)
            } else {
                canvas.rotate(-angle, coordinate.first, coordinate.second)
            }
        }
    }

    private fun getTextCoordinates(angle: Float): Pair<Float, Float> {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width - paint.strokeWidth) / 2f
        val x = centerX + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
        val y = centerY + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
        return Pair(x, y)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val centerX = (width / 2f)
        val centerY = (height / 2f)
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                val angle = (Math.toDegrees(atan2(
                    y - centerY,
                    x - centerX
                ).toDouble()) + 360) % 360
                Log.d("Angle", "$angle")
                buttonClicked = (angle / (360 / colorList.size)).toInt()
                val color = colorList[buttonClicked].toString()
                listener?.onClick(buttonClicked, color)
                invalidate()
            }
            /*MotionEvent.ACTION_UP -> {
                buttonClicked = -1
                listener?.onClick(buttonClicked, "Click\non the color")
                invalidate()
            }*/
        }
        return true
    }

    interface Listener {
        fun onClick(index: Int, color: String)
    }
}

