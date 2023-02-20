package com.coming.app.fitmax.camera

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources.Theme
import android.graphics.*
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.coming.app.fitmax.data.BodyPart
import com.coming.app.fitmax.data.CountSquat
import com.coming.app.fitmax.data.Person
import java.lang.reflect.Modifier


object VisualizationUtils {
    /** Radius of circle used to draw keypoints.  */
    private const val CIRCLE_RADIUS = 10f

    /** Width of line used to connected two keypoints.  */
    private val FADE_MILLISECONDS: Int = 600
    private const val FADE_STEP = 120
    private val ALPHA_STEP = 100 / (FADE_MILLISECONDS / FADE_STEP)
    private val ALPHA_STEP1 = 225 / (FADE_MILLISECONDS / FADE_STEP)
    private var currentAlpha = 0
    private var currentAlpha1 = 0
    private val PROGRESS_MILLISECONDS: Int = 1000
    private const val PROGRESS_STEP = 60
    private val PROGRESS = 180 / (FADE_MILLISECONDS / FADE_STEP)
    private var progress = 360f

    /** Pair of keypoints to draw lines between.  */
    private var bodyJoints = listOf(
//        Pair(BodyPart.NOSE, BodyPart.LEFT_EYE),
//        Pair(BodyPart.NOSE, BodyPart.RIGHT_EYE),
//        Pair(BodyPart.LEFT_EYE, BodyPart.LEFT_EAR),
//        Pair(BodyPart.RIGHT_EYE, BodyPart.RIGHT_EAR),
//        Pair(BodyPart.NOSE, BodyPart.LEFT_SHOULDER),
//        Pair(BodyPart.NOSE, BodyPart.RIGHT_SHOULDER),
        Pair(BodyPart.LEFT_SHOULDER, BodyPart.LEFT_ELBOW),
        Pair(BodyPart.LEFT_ELBOW, BodyPart.LEFT_WRIST),
        Pair(BodyPart.RIGHT_SHOULDER, BodyPart.RIGHT_ELBOW),
        Pair(BodyPart.RIGHT_ELBOW, BodyPart.RIGHT_WRIST),
        Pair(BodyPart.LEFT_WRIST, BodyPart.RIGHT_WRIST),
        Pair(BodyPart.LEFT_SHOULDER, BodyPart.RIGHT_SHOULDER),
//        Pair(BodyPart.LEFT_SHOULDER, BodyPart.LEFT_HIP),
//        Pair(BodyPart.RIGHT_SHOULDER, BodyPart.RIGHT_HIP),
//        Pair(BodyPart.LEFT_HIP, BodyPart.RIGHT_HIP),
//        Pair(BodyPart.LEFT_HIP, BodyPart.LEFT_KNEE),
//        Pair(BodyPart.LEFT_KNEE, BodyPart.LEFT_ANKLE),
//        Pair(BodyPart.RIGHT_HIP, BodyPart.RIGHT_KNEE),
//        Pair(BodyPart.RIGHT_KNEE, BodyPart.RIGHT_ANKLE)
    )

    private var countSquat = CountSquat()

    fun calculateCoordinateLength(x1: Float, x2: Float, y1: Float, y2: Float): Double {
        val X1X2 = (x1 - x2) * (x1 - x2)
        val Y1Y2 = (y1 - y2) * (y1 - y2)
        val XY = Math.sqrt((X1X2 + Y1Y2).toDouble())
        return XY
    }

    fun sweepAngle(percentagePerson: Float): Float {
        var currentPos = percentagePerson
        if (percentagePerson > 0.8) {
            currentPos = 0.8F
        }
        if (percentagePerson < 0.6) {
            currentPos = 0.6f
        }
        val angle = (0.8 - currentPos)
        var percent = 0.2 / 180
        val size = (angle / percent)
        return (360 - size).toFloat()
    }

    fun sweepAngle1(percentagePerson: Float): Float {
        var currentPos = percentagePerson
        if (percentagePerson > 0.8) {
            currentPos = 0.8F
        }
        if (percentagePerson < 0.6) {
            currentPos = 0.6f
        }
        val angle = (0.8 - currentPos)
        var percent = 0.2 / 180
        val size = (angle / percent)
        return (360 - size).toFloat()
    }

    fun alphaOpacity(alpla: Int, alphaStep: Int): Int {
        var alpha = 0
        if (alphaStep == alpla) {
            alpha = alphaStep
        } else {
            alpha = alpla
        }
        return alpha
    }

    // Draw line and point indicate body pose
    fun drawBodyKeypoints(input: Bitmap, person: Person, isSquat: Boolean): Bitmap {
        val paintCircle = Paint().apply {
            strokeWidth = CIRCLE_RADIUS
            color = Color.GRAY
            style = Paint.Style.STROKE
        }
        val paintText = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
            textSize = 70f
        }
        val paintPoint = Paint().apply {
            color = Color.BLUE
        }
        val paintText1 = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            textSize = 70f
            textAlign = Paint.Align.CENTER

        }

        val output = input.copy(Bitmap.Config.ARGB_8888, true)
        val originalSizeCanvas = Canvas(output)
        // diem vai
        val keyPointLeftShoulder = person.keyPoints[5].coordinate

        // diem hong
        val keyPointLeftHip = person.keyPoints[11].coordinate

        //diem chan
        val keyPointLeftAnkle = person.keyPoints[15].coordinate

        val bodyPartLeftShoulderHip = calculateCoordinateLength(
            keyPointLeftShoulder.x, keyPointLeftHip.x, keyPointLeftShoulder.y, keyPointLeftHip.y
        )
        val bodyPartLeftHipAnkle = calculateCoordinateLength(
            keyPointLeftHip.x, keyPointLeftAnkle.x, keyPointLeftHip.y, keyPointLeftAnkle.y
        )
        val recipe = (bodyPartLeftShoulderHip / 2.5) * 4
        val percentagePerson = bodyPartLeftHipAnkle / recipe
        var width = input.width
        var height = input.height
        val rectf =
            RectF((width / 2) - 100f, height - 400f, (width / 2).toFloat() + 100f, height - 200f)

        val checkScore = person.keyPoints.toList().all { it.score > 0.3 }
        person.keyPoints.forEach { point ->
            originalSizeCanvas.drawCircle(
                point.coordinate.x, point.coordinate.y, CIRCLE_RADIUS, paintCircle
            )
        }

        originalSizeCanvas.drawText(
            "progress bar:${sweepAngle(percentagePerson.toFloat())}",
            10f,
            250f,
            paintText
        )



        if (currentAlpha != 0 && currentAlpha1 != 0) {
            sweepAngle1(percentagePerson.toFloat())
            originalSizeCanvas.drawRoundRect(
                (width / 2) - 150f,
                height - 450f,
                (width / 2).toFloat() + 150f,
                height - 150f,
                20.0f,
                20.0f,
                Paint().apply {
                    color = Color.parseColor("#66000000")
                    style = Paint.Style.FILL
                    alpha = alphaOpacity(currentAlpha, 100)
                }
            );

            originalSizeCanvas.drawArc(rectf, 0f, 360f, false, Paint().apply {
                color = Color.WHITE
                strokeWidth = 40f
                isAntiAlias = true
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
                alpha = alphaOpacity(currentAlpha, 100)
            })

            originalSizeCanvas.drawArc(rectf,
                270f,
                sweepAngle1(progress),
//                sweepAngle(percentagePerson.toFloat()),
                false,
                Paint().apply {
                    color = Color.WHITE
                    strokeWidth = 40f
                    isAntiAlias = true
                    style = Paint.Style.STROKE
                    strokeCap = Paint.Cap.ROUND
                    alpha = alphaOpacity(currentAlpha1, 225)
                })
            originalSizeCanvas.drawText(
                countSquat.squat.toString(),
                (width / 2).toFloat(),
                (height - 280).toFloat(),
                Paint().apply {
                    color = Color.WHITE
                    style = Paint.Style.FILL
                    textSize = 70f
                    textAlign = Paint.Align.CENTER
                    alpha = alphaOpacity(currentAlpha1, 225)
                }
            )
        }
        if (isSquat && checkScore) {
            if (currentAlpha < 100 && currentAlpha1 < 225) {
                currentAlpha += ALPHA_STEP
                currentAlpha1 += ALPHA_STEP1
            }
            if (percentagePerson < 0.6 && checkScore) {
                countSquat.setCheckSquat(1, false);
            }
            if (percentagePerson > 0.8 && checkScore) {
                countSquat.setValue(true)
            }

        } else {
            if (
                currentAlpha > 0 && currentAlpha1 > 0
            ) {
                currentAlpha -= ALPHA_STEP
                currentAlpha1 -= ALPHA_STEP1
            }

        }


        return output
    }


}


