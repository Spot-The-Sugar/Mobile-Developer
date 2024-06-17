package com.example.spotthesugar.ui.camera

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import com.example.spotthesugar.R
import com.example.spotthesugar.databinding.ActivityCameraBinding
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var barcodeScanner: BarcodeScanner

    private var firstCall = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
        //startCamera2()
    }

    private fun startCamera2() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_EAN_13)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)

        val analyzer = MlKitAnalyzer(
            listOf(barcodeScanner),
            COORDINATE_SYSTEM_VIEW_REFERENCED,
            ContextCompat.getMainExecutor(this)
        ) { result: MlKitAnalyzer.Result? ->
            showResult2(result)
        }

        val cameraController = LifecycleCameraController(baseContext)
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            analyzer
        )
        cameraController.bindToLifecycle(this)
        binding.viewFinder.controller = cameraController
    }

    private fun startCamera() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_EAN_13)  // Mengubah format ke EAN-13
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)

        val analyzer = MlKitAnalyzer(
            listOf(barcodeScanner),
            COORDINATE_SYSTEM_VIEW_REFERENCED,
            ContextCompat.getMainExecutor(this)
        ) { result: MlKitAnalyzer.Result? ->
            showResultEAN13(result)
        }

        val cameraController = LifecycleCameraController(baseContext)
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(this),
            analyzer
        )
        cameraController.bindToLifecycle(this)
        binding.viewFinder.controller = cameraController
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showResultEAN13(result: MlKitAnalyzer.Result?) {
        if (firstCall) {
            val barcodeResults = result?.getValue(barcodeScanner)
            if ((barcodeResults != null) &&
                (barcodeResults.isNotEmpty()) &&
                (barcodeResults.first() != null)
            ) {
                firstCall = false
                val barcode = barcodeResults[0]

                if (barcode.format == Barcode.FORMAT_EAN_13) {
                    // Tampilkan Toast dengan nilai barcode
                    Toast.makeText(this, "EAN-13 Barcode detected: ${barcode.rawValue}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Unsupported barcode format", Toast.LENGTH_SHORT).show()
                }

                // Reset firstCall after a delay to allow for multiple scans
                binding.viewFinder.postDelayed({
                    firstCall = true
                }, 2000) // Adjust delay as needed
            }
        }
    }

    private fun showResult2(result: MlKitAnalyzer.Result?){
        if (firstCall) {
            val barcodeResults = result?.getValue(barcodeScanner)
            if ((barcodeResults != null) &&
                (barcodeResults.size != 0) &&
                (barcodeResults.first() != null)
            ) {
                firstCall = false
                val barcode = barcodeResults[0]

                val alertDialog = AlertDialog.Builder(this)
                alertDialog
                    .setMessage(barcode.rawValue)
                    .setPositiveButton(
                        "Buka"
                    ) { _, _ ->
                        firstCall = true
                        when (barcode.valueType) {
                            Barcode.TYPE_URL -> {
                                val openBrowserIntent = Intent(Intent.ACTION_VIEW)
                                openBrowserIntent.data = Uri.parse(barcode.url?.url)
                                startActivity(openBrowserIntent)
                            }

                            else -> {
                                Toast.makeText(this, "Unsupported data type", Toast.LENGTH_SHORT)
                                    .show()
                                startCamera()
                            }
                        }
                    }
                    .setNegativeButton("Scan lagi") { _, _ ->
                        firstCall = true
                    }
                    .setCancelable(false)
                    .create()
                alertDialog.show()
            }
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}