package com.coming.app.fitmax.ui.pose_estimation

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.coming.app.fitmax.R
import com.coming.app.fitmax.camera.PoseEstimationAnalyzer
import com.coming.app.fitmax.data.Device
import com.coming.app.fitmax.databinding.FragmentPoseEstimationBinding
import com.coming.app.fitmax.models.MoveNet
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class PoseEstimationFragment : Fragment() {

    private lateinit var binding: FragmentPoseEstimationBinding

    private lateinit var cameraExecutor: ExecutorService

    private val poseDetector by lazy {
        MoveNet.create(requireContext(), Device.CPU)
    }
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
            } else {
//                Log.d("camera permission", "no")
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPoseEstimationBinding.inflate(inflater)
        cameraExecutor = Executors.newSingleThreadExecutor()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkCameraPermission()
    }


    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
//                Log.d("request camera", "OK")
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val imageAnalyzer = ImageAnalysis.Builder().setTargetResolution(Size(720, 1600))
                .build().also {
                    it.setAnalyzer(
                        cameraExecutor, PoseEstimationAnalyzer(
                            poseDetector,
                            binding.surfaceView2
                        )
                    )
                }
            // Select back camera as a default
//            val cameraSelector = CameraSelector
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.d("error camera", exc.toString())
            }
        }, ContextCompat.getMainExecutor(requireContext()))

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}
