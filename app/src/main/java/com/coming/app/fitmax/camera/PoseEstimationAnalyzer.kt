package com.coming.app.fitmax.camera

import android.graphics.*
import android.util.Log
import android.view.SurfaceView
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.coming.app.fitmax.data.Person
import com.coming.app.fitmax.models.PoseDetector
import java.io.ByteArrayOutputStream


class PoseEstimationAnalyzer(
    private val poseDetector: PoseDetector,
    private val surfaceView: SurfaceView
) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {

        val rotatedBitmap = image.toRotatedBitmap()

        processImage(rotatedBitmap, poseDetector, surfaceView)

        image.close()
    }

}

// This function processes camera input images to estimate human poses and draw lines on them
private fun processImage(bitmap: Bitmap, detector: PoseDetector, surfaceView: SurfaceView) {

    val person: Person = detector.estimateSinglePose(bitmap)
    visualize(person, bitmap, surfaceView)
}

private const val MIN_CONFIDENCE = .3f


// This Function draw lines based on human pose
private fun visualize(person: Person, bitmap: Bitmap, surfaceView: SurfaceView) {
    var outputBitmap = bitmap
    var count: Int = 0
    var isSquat = checkIsSquat(person)

    if (person.score > MIN_CONFIDENCE) {
        outputBitmap = VisualizationUtils.drawBodyKeypoints(bitmap, person, isSquat)
    }

    val holder = surfaceView.holder
    val surfaceCanvas = holder.lockCanvas()
    surfaceCanvas?.let { canvas ->
        val screenWidth: Int
        val screenHeight: Int
        val left: Int
        val top: Int
        val ratio = outputBitmap.height.toFloat() / outputBitmap.width
        screenWidth = canvas.width
        left = 0
        screenHeight = (canvas.width * ratio).toInt()
        top = (canvas.height - screenHeight) / 2

        val right: Int = left + screenWidth
        val bottom: Int = top + screenHeight
        canvas.drawBitmap(
            outputBitmap, Rect(0, 0, outputBitmap.width, outputBitmap.height),
            Rect(left, top, right, bottom), null
        )
        surfaceView.holder.unlockCanvasAndPost(canvas)
    }
}

fun checkIsSquat(person: Person): Boolean {
    //mat
    val keyPointLeftEye = person.keyPoints[1].coordinate
    val keyPointRightEye = person.keyPoints[2].coordinate
    //co tay
    val keyPointLeftWRIST = person.keyPoints[9].coordinate
    val keyPointRightWRIST = person.keyPoints[10].coordinate
    //khuyu tay
    val keyPointLeftElbow = person.keyPoints[7].coordinate
    val keyPointRightElbow = person.keyPoints[8].coordinate
    //vai
    val keyPointLeftShoulder = person.keyPoints[5].coordinate
    val keyPointRightShoulder = person.keyPoints[6].coordinate

    val wristLeftRight = calculateCoordinateLength(
        keyPointLeftWRIST.x,
        keyPointRightWRIST.x,
        keyPointLeftWRIST.y,
        keyPointRightWRIST.y
    )
    val bodyPartLeftRightShoulder = calculateCoordinateLength(
        keyPointLeftShoulder.x,
        keyPointRightShoulder.x,
        keyPointLeftShoulder.y,
        keyPointRightShoulder.y
    )
    val eyeShoulder = (keyPointLeftShoulder.y - keyPointLeftEye.y)
    val eyeWrist = (keyPointLeftWRIST.y - keyPointLeftEye.y)

//    Log.d("Shoulder===>", (bodyPartLeftRightShoulder *4/5).toString())
////    Log.d("Wrist===>", bodyPartLeftRightWrist.toString())
//    Log.d("Wrist2===>", wristLeftRight.toString())
//    Log.d("eyeShoulder==>", eyeShoulder.toString())
//    Log.d("eyeWrist===>", eyeWrist.toString())


    if (wristLeftRight <= bodyPartLeftRightShoulder*4/5 ) {
        if ( keyPointLeftElbow.y > keyPointLeftWRIST.y && keyPointRightElbow.y > keyPointRightWRIST.y) {
            return true
        }
    }
    return false
}

fun calculateCoordinateLength(x1: Float, x2: Float, y1: Float, y2: Float): Double {
    val X1X2 = (x1 - x2) * (x1 - x2)
    val Y1Y2 = (y1 - y2) * (y1 - y2)
    val XY = Math.sqrt((X1X2 + Y1Y2).toDouble())
    return XY
}

// This function first convert ImageProxy to Bitmap and then Rotate it and return Rotated bitmap
fun ImageProxy.toRotatedBitmap(): Bitmap {
    val yBuffer = planes[0].buffer // Y
    val vuBuffer = planes[2].buffer // VU


    val ySize = yBuffer.remaining()
    val vuSize = vuBuffer.remaining()
    val nv21 = ByteArray(ySize + vuSize)

    yBuffer.get(nv21, 0, ySize)
    vuBuffer.get(nv21, ySize, vuSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
    val imageBytes = out.toByteArray()
    val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    val rotateMatrix = Matrix()
    rotateMatrix.postRotate(270.0f)

    return Bitmap.createBitmap(
        imageBitmap, 0, 0, this.width, this.height,
        rotateMatrix, false
    )
}

