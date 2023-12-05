package com.bangkit23.estetika.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bangkit23.estetika.ui.scanresult.ScanResultActivity.Companion.EXTRA_RESULT
import androidx.core.content.ContextCompat
import com.bangkit23.estetika.R
import com.bangkit23.estetika.databinding.FragmentScanBinding
import com.bangkit23.estetika.ml.ModelBatik
import com.bangkit23.estetika.ui.scanresult.ScanResultActivity
import com.bangkit23.estetika.util.getImageUri
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

@AndroidEntryPoint
@Suppress("DEPRECATION")
class ScanFragment : Fragment() {

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding

    private lateinit var bitmap: Bitmap
    private lateinit var imageView: ImageView
    private var currentImageUri: Uri? = null

    private lateinit var viewModel: ScanViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ScanViewModel::class.java]

        imageView = binding?.imageView!!

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val fileName = "labels_batik.txt"
        val inputString = requireContext().assets.open(fileName).bufferedReader().use{it.readText()}
        var townlist = inputString.split("\n")

        binding?.btnCamera?.setOnClickListener { startCamera() }
        binding?.btnGallery?.setOnClickListener { startGallery() }
        binding?.buttonScan?.setOnClickListener {
            val model = ModelBatik.newInstance(requireContext())
            bitmap = (imageView.drawable as BitmapDrawable).bitmap
            var byteBuffer = convertBitmapToByteBuffer(bitmap)
// Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            var max = getMax(outputFeature0.floatArray)
//            textView.text = townlist[max]
            binding?.hasil?.text = townlist[max]
            val intent = Intent(requireActivity(), ScanResultActivity::class.java)
            intent.putExtra(EXTRA_RESULT, townlist[max]);

            startActivity(intent)
            model.close()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding?.imageView?.setImageURI(it)
            bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val imgData = ByteBuffer.allocateDirect(4 * 1 * 224 * 224 * 3)
        imgData.order(ByteOrder.nativeOrder())

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        imgData.rewind()

        val intValues = IntArray(224 * 224)
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)

        var pixel = 0
        for (i in 0 until 224) {
            for (j in 0 until 224) {
                val value = intValues[pixel++]
                imgData.putFloat((value shr 16 and 0xFF) * (1f / 255f))
                imgData.putFloat((value shr 8 and 0xFF) * (1f / 255f))
                imgData.putFloat((value and 0xFF) * (1f / 255f))
            }
        }

        return imgData
    }

    private fun getMax(arr:FloatArray) : Int{
        var index = 0
        var min = 0.0f

        for(i in 0..21){
            if(arr[i]>min){
                index = i
                min = arr[i]
            }
        }
        return index
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}