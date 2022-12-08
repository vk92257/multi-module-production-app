package com.bjs.pdanative.presentation.ui.camera

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.display.DisplayManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import com.bjs.pdanative.R

typealias FrontCameraLumaListener = (luma: Double) -> Unit


class FrontCameraFragment : Fragment() {

    private val extensionWhiteList = arrayOf("JPG")

    private lateinit var viewFinder: PreviewView
    private lateinit var container: ConstraintLayout
    private lateinit var outputDirectory: File
    private lateinit var broadcastManager: LocalBroadcastManager
    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_FRONT
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var imageList = ArrayList<ImagesModel>()
    private val cameraViewModel: CameraViewModel by activityViewModels()

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    /*Blocking camera operations are performed using this executor*/
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    /*Volume down button receiver used to trigger shutter*/
    private val volumeDownReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1!!.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN)) {
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
//                    val shutter = container.findViewById<ImageButton>(R.id.camera_capture_button)
//                    shutter.simulateClick()
                }
                KeyEvent.KEYCODE_BACK -> {
/*
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigateUp()
*/
                }
            }
        }
    }


    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayChanged(p0: Int) = view?.let { view ->
            if (p0 == this@FrontCameraFragment.displayId) {
                Log.d("FrontCameraFragment", "Rotation changed ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        } ?: Unit

        override fun onDisplayAdded(p0: Int) = Unit
        override fun onDisplayRemoved(p0: Int) = Unit
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        broadcastManager.unregisterReceiver(volumeDownReceiver)
        displayManager.unregisterDisplayListener(displayListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view as ConstraintLayout
        viewFinder = container.findViewById(R.id.view_finder)
        broadcastManager = LocalBroadcastManager.getInstance(view.context)
        //set up intent filter to receive events from activity
        val filter = IntentFilter().apply { addAction(KEY_EVENT_ACTION) }
        broadcastManager.registerReceiver(volumeDownReceiver, filter)
        // Every time orientation of device changes, update rotation for use cases
        displayManager.registerDisplayListener(displayListener, null)
        //Determine output directory
        outputDirectory = getOutputDirectory(requireContext())

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        viewFinder.post {
            displayId = viewFinder.display.displayId
            updateCameraUI()
            bindCameraUseCases()
        }
    }

    private fun updateCameraUI() {

        container.findViewById<ConstraintLayout>(R.id.camera_ui_container)?.let {
            container.removeView(it)
        }

        val controls = View.inflate(requireContext(), R.layout.camera_ui_container, container)

        lifecycleScope.launch(Dispatchers.IO)
        {
            outputDirectory.listFiles { file ->
                extensionWhiteList.contains(file.extension.uppercase(Locale.ROOT))
            }.maxOrNull()?.let {

            }
        }

        controls.findViewById<ImageButton>(R.id.camera_capture_button).setOnClickListener {

            imageCapture?.let { imageCapture ->

                val photoFile = createFile(
                    outputDirectory,
                    FILENAME,
                    PHOTO_EXTENSION
                )

                val metadata = ImageCapture.Metadata().apply {

                    // Mirror image when using the front camera
                    isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
                }

                val outputOptions =
                    ImageCapture.OutputFileOptions.Builder(photoFile).setMetadata(metadata).build()

                imageCapture.takePicture(
                    outputOptions,
                    cameraExecutor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                            requireActivity().runOnUiThread {
                                val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)

                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                    requireActivity().sendBroadcast(
                                        Intent(
                                            android.hardware.Camera.ACTION_NEW_PICTURE,
                                            savedUri
                                        )
                                    )
                                }

                                val mimeType = MimeTypeMap.getSingleton()
                                    .getMimeTypeFromExtension(savedUri.toFile().extension)
                                MediaScannerConnection.scanFile(
                                    context, arrayOf(savedUri.toString()), arrayOf(mimeType)
                                ) { _, uri ->
                                    Log.d(
                                        FrontCameraFragment.TAG,
                                        "Image capture scanned into media store: $uri"
                                    )
                                }
                                imageList.add(ImagesModel(photoFile.absolutePath, savedUri))
                                cameraViewModel.setUserProfile(imageList)
/*
                            if (mListener != null) {
                                mListener!!.getCapturedImages(imageList)
                            }
*/
                                activity?.runOnUiThread {
                                    Navigation.findNavController(
                                        requireActivity(),
                                        R.id.nav_host_fragment
                                    )
                                        .navigate(R.id.action_frontCameraFragment_to_loginSuccessFragment)
                                }
                            }
                        }
                        override fun onError(exception: ImageCaptureException) {

                        }


                    })

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Display flash animation to indicate that photo was captured
                    container.postDelayed({
                        container.foreground = ColorDrawable(Color.WHITE)
                        container.postDelayed(
                            { container.foreground = null }, ANIMATION_FAST_MILLIS
                        )
                    }, ANIMATION_SLOW_MILLIS)
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            controls.findViewById<ImageButton>(R.id.camera_capture_button).performClick()
        }, 4000)

        // Listener for button used to view the most recent photo
        controls.findViewById<ImageButton>(R.id.photo_view_button).visibility = View.GONE
        controls.findViewById<ImageButton>(R.id.camera_switch_button).visibility = View.GONE
        controls.findViewById<ImageButton>(R.id.camera_capture_button).visibility = View.GONE


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateCameraUI()
    }


    private fun bindCameraUseCases() {

        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = viewFinder.display.rotation

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {

            val cameraProvide: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation).build()

            preview?.setSurfaceProvider(viewFinder.surfaceProvider)

            imageCapture =
                ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .setTargetAspectRatio(screenAspectRatio).setTargetRotation(rotation).build()

            imageAnalyzer = ImageAnalysis.Builder().setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation).build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer {
                    })
                }

            cameraProvide.unbindAll()
            try {
                // A variable number of use-cases can be passed here -
                // camera provides access to CameraControl & CameraInfo
                camera = cameraProvide.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }


    companion object {

        private const val TAG = "CameraXBasic"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        private var mListener: OnCapturedImages? = null


        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
            }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }

        /** Helper function used to create a timestamped file */
        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(
                baseFolder, SimpleDateFormat(format, Locale.US)
                    .format(System.currentTimeMillis()) + extension
            )

        fun setOnCapturedImageList(listener: OnCapturedImages) {
            mListener = listener
        }
    }

    private class LuminosityAnalyzer(listener: FrontCameraLumaListener? = null) :
        ImageAnalysis.Analyzer {
        private val frameRateWindow = 8
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners =
            ArrayList<FrontCameraLumaListener>().apply { listener?.let { add(it) } }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
            private set

        /**
         * Used to add listeners that will be called with each luma computed
         */
        fun onFrameAnalyzed(listener: FrontCameraLumaListener) = listeners.add(listener)

        /**
         * Helper extension function used to extract a byte array from an image plane buffer
         */
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero

            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        /**
         * Analyzes an image to produce a result.
         *
         * <p>The caller is responsible for ensuring this analysis method can be executed quickly
         * enough to prevent stalls in the image acquisition pipeline. Otherwise, newly available
         * images will not be acquired and analyzed.
         *
         * <p>The image passed to this method becomes invalid after this method returns. The caller
         * should not store external references to this image, as these references will become
         * invalid.
         *
         * @param image image being analyzed VERY IMPORTANT: Analyzer method implementation must
         * call image.close() on received images when finished using them. Otherwise, new images
         * may not be received or the camera may stall, depending on back pressure setting.
         *
         */
        override fun analyze(image: ImageProxy) {
            // If there are no listeners attached, we don't need to perform analysis
            try {
                if (listeners.isEmpty()) {
                    image.close()
                    return
                }

                // Keep track of frames analyzed
                val currentTime = System.currentTimeMillis()
                frameTimestamps.push(currentTime)

                // Compute the FPS using a moving average
                while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
                val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
                val timestampLast = frameTimestamps.peekLast() ?: currentTime
                framesPerSecond = 1.0 / ((timestampFirst - timestampLast) /
                        frameTimestamps.size.coerceAtLeast(1).toDouble()) * 1000.0

                // Analysis could take an arbitrarily long amount of time
                // Since we are running in a different thread, it won't stall other use cases

                lastAnalyzedTimestamp = frameTimestamps.first

                // Since format in ImageAnalysis is YUV, image.planes[0] contains the luminance plane
                val buffer = image.planes[0].buffer

                // Extract image data from callback object
                val data = buffer.toByteArray()

                // Convert the data into an array of pixel values ranging 0-255
                val pixels = data.map { it.toInt() and 0xFF }

                // Compute average luminance for the image
                val luma = pixels.average()

                // Call all listeners with new value
                listeners.forEach { it(luma) }

                image.close()
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "unable to process image")
                e.printStackTrace()
            }
        }
    }


}