package com.coming.app.fitmax.models

import android.graphics.Bitmap
import com.coming.app.fitmax.data.Person


interface PoseDetector : AutoCloseable {

    fun estimateSinglePose(bitmap: Bitmap): Person

    fun lastInferenceTimeNanos(): Long
}