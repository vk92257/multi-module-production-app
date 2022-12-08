package com.bjs.pdanative.presentation.ui.camera

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.display.DisplayManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.camera.core.*
import androidx.camera.core.AspectRatio.RATIO_16_9
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bjs.hgv.utils.ToastUtil
import com.bjs.pdanative.common.BUNDLE_IMAGES_LIST
import com.bjs.pdanative.common.BUNDLE_IS_FROM_ACTIVITY
import com.bjs.pdanative.R
import com.bjs.pdanative.common.SafeClickListener.Companion.setSafeOnClickListener
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


typealias LumaListener = (luma: Double) -> Unit

const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"

class CameraFragment : Fragment() {

    private lateinit var viewFinder: PreviewView
    private lateinit var container: ConstraintLayout
    private lateinit var outputDirectory: File
    private lateinit var broadcastManager: LocalBroadcastManager
    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private lateinit var ivFlashMode: ImageView
    private var imageList = ArrayList<ImagesModel>()
    private val cameraViewModel: CameraViewModel by activityViewModels()
    private var flashEnabled = false
    private var requiredPhotos: Int = 0
    private var isFromActivity = false

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    /*Blocking camera operations are performed using this executor*/
    private val cameraExecutor = Executors.newSingleThreadExecutor()


    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayChanged(p0: Int) = view?.let { view ->
            if (p0 == this@CameraFragment.displayId) {
                Log.d("CameraFrament", "Rotation changed ${view.display.rotation}")
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
        displayManager.unregisterDisplayListener(displayListener)
    }

    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event

                if (isFromActivity) {
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                } else
                    Navigation.findNavController(
                        requireActivity(),
                        R.id.nav_host_fragment
                    ).navigateUp()


            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view as ConstraintLayout
        viewFinder = container.findViewById(R.id.view_finder)
        broadcastManager = LocalBroadcastManager.getInstance(view.context)
        //set up intent filter to receive events from activity
        // Every time orientation of device changes, update rotation for use cases
        displayManager.registerDisplayListener(displayListener, null)


        //Determine output directory
        outputDirectory = getOutputDirectory(requireContext())
        viewFinder.post {
            displayId = viewFinder.display.displayId
            updateCameraUI()
            bindCameraUseCases()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun updateCameraUI() {

        container.findViewById<ConstraintLayout>(R.id.camera_ui_container)?.let {
            container.removeView(it)
        }

        val controls = View.inflate(requireContext(), R.layout.camera_ui_container, container)
        ivFlashMode = controls.findViewById(R.id.iv_flash_control)
        ivFlashMode.visibility = View.VISIBLE
        controls.findViewById<ImageButton>(R.id.camera_capture_button).setSafeOnClickListener {
            imageCapture?.let { imageCapture ->

                val photoFile = createFile(outputDirectory)

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
                            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                            val mimeType = MimeTypeMap.getSingleton()
                                .getMimeTypeFromExtension(savedUri.toFile().extension)
                            MediaScannerConnection.scanFile(
                                context, arrayOf(savedUri.toFile().absolutePath), arrayOf(mimeType)
                            ) { _, uri ->
                                Log.d(TAG, "Image capture scanned into media store: $uri")
                            }

                            //TODO need file compression here
                            imageList.add(ImagesModel(photoFile.absolutePath, savedUri))
                            requireActivity().runOnUiThread {
                                openPreviewPopUp()
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            exception.printStackTrace()
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

        // Listener for button used to view the most recent photo
        controls.findViewById<ImageButton>(R.id.photo_view_button).visibility = View.GONE
        controls.findViewById<ImageButton>(R.id.camera_switch_button).visibility = View.GONE
    }


    /**
     * showing the list of captured photos as a viewer
     * finish will dismiss the viewer and camera
     */
    private fun showImageListPopUp(onFinish: () -> Unit) {
        val fullscreenDialog =
            Dialog(requireContext(), R.style.DialogFullscreen)
        fullscreenDialog.setContentView(R.layout.dialog_camera_viewer)
        val rvImages = fullscreenDialog.findViewById<RecyclerView>(R.id.rv_thumbnails)
        val btnAdd = fullscreenDialog.findViewById<RelativeLayout>(R.id.rl_add)
        val fabAdd = fullscreenDialog.findViewById<FloatingActionButton>(R.id.fab_photo)
        val ivImage = fullscreenDialog.findViewById<ImageView>(R.id.iv_image)
        val btnFinish = fullscreenDialog.findViewById<FloatingActionButton>(R.id.fab_finish)
        Glide.with(requireContext()).load(imageList[imageList.lastIndex].imageUri).into(ivImage)
        fabAdd.setSafeOnClickListener { fullscreenDialog.dismiss() }
        btnAdd.setSafeOnClickListener { fullscreenDialog.dismiss() }
        btnFinish.setSafeOnClickListener {
            if (imageList.size >= requiredPhotos) {
                fullscreenDialog.dismiss()
                onFinish.invoke()
            } else {
                ToastUtil.showShortToast(
                    requireContext(),
                    "Please capture at least $requiredPhotos photos"
                )
            }
        }
        rvImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val imageAdapter = ImageAdapter(requireContext(), imageList, object :
            ImageAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                Glide.with(requireContext()).load(imageList[position].imageUri)
                    .into(ivImage)
            }

            override fun onItemDeleteClick(position: Int) {
                if (imageList.size == 0) {
                    fullscreenDialog.dismiss()
                } else {
                    Glide.with(requireContext()).load(imageList[imageList.lastIndex].imageUri)
                        .into(ivImage)
                }
            }
        }, isDeleteFromDialog = true, isDeleteButtonNeeded = true)
        rvImages.adapter = imageAdapter
        fullscreenDialog.setCanceledOnTouchOutside(false)
        fullscreenDialog.setCancelable(true)
        fullscreenDialog.show()
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
        cameraProviderFuture.addListener({
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

                if (camera?.cameraInfo?.hasFlashUnit() == true) {
                    ivFlashMode.visibility = View.VISIBLE
                    ivFlashMode.setOnClickListener {
                        camera!!.cameraControl.enableTorch(!flashEnabled)
                    }
                    camera!!.cameraInfo.torchState.observe(viewLifecycleOwner) {
                        it?.let { torchState ->
                            if (torchState == TorchState.ON) {
                                flashEnabled = true
                                ivFlashMode.setImageResource(R.drawable.ic_flash_on)
                            } else {
                                flashEnabled = false
                                ivFlashMode.setImageResource(R.drawable.ic_flash_off)
                            }
                        }
                    }
                }


            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        requireActivity().intent?.let {
            isFromActivity = it.getBooleanExtra(BUNDLE_IS_FROM_ACTIVITY, false)
            if (!isFromActivity) {
                imageList = cameraViewModel.capturedImageList.value ?: ArrayList()
                requiredPhotos = cameraViewModel.requirePhotos.value ?: 0
            } else
                imageList =
                    it.getParcelableArrayListExtra<ImagesModel>(BUNDLE_IMAGES_LIST) as ArrayList<ImagesModel>
        }
        if (imageList.size > 0) {
            openPreviewPopUp()
        }
    }


    fun openPreviewPopUp() {
        try {
            camera!!.cameraControl.enableTorch(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        requireActivity().runOnUiThread {
            showImageListPopUp {
                if (isFromActivity) {
                    itemListener!!.getImageList(imageList)
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                } else {
                    cameraViewModel.setCapturedImageList(imageList)
                    Navigation.findNavController(
                        requireActivity(),
                        R.id.nav_host_fragment
                    )
                        .navigateUp()
                }
            }
        }

    }


    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)

        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return RATIO_16_9
    }


    companion object {

        private const val TAG = "CameraXBasic"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        private var itemListener: ItemListenerFromActivity? = null

        interface ItemListenerFromActivity {
            fun getImageList(imageList: ArrayList<ImagesModel>)
        }

        fun setListener(itemListener: ItemListenerFromActivity) {
            this.itemListener = itemListener
        }

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
        private fun createFile(baseFolder: File): File =
            File(
                baseFolder, SimpleDateFormat(FILENAME, Locale.US)
                    .format(System.currentTimeMillis()) + PHOTO_EXTENSION
            )

    }

    private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer {
        private val frameRateWindow = 8
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
            private set


        /**
         * Used to add listeners that will be called with each luma computed
         */
        fun onFrameAnalyzed(listener: LumaListener) = listeners.add(listener)

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