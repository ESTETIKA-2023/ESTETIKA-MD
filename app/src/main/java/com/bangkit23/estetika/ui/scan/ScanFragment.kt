package com.bangkit23.estetika.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bangkit23.estetika.R
import com.bangkit23.estetika.databinding.FragmentScanBinding
import com.bangkit23.estetika.ui.scanresult.ScanResultActivity
import com.bangkit23.estetika.util.animateVisibility
import com.bangkit23.estetika.util.getImageUri
import com.bangkit23.estetika.util.uriToFile
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
@Suppress("DEPRECATION")
class ScanFragment : Fragment() {

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding

    private var token: String = ""
    private lateinit var bitmap: Bitmap
    private lateinit var imageView: ImageView
    private var currentImageUri: Uri? = null

    private val viewModel: ScanViewModel by viewModels()

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

        imageView = binding?.imageView!!

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getAuthToken().collect { authToken ->
                    if (!authToken.isNullOrEmpty()) token = authToken
                }
            }
        }

        binding?.btnCamera?.setOnClickListener { startCamera() }
        binding?.btnGallery?.setOnClickListener { startGallery() }
        binding?.buttonScan?.setOnClickListener {
            uploadImage()
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

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            setLoadingState(true)
            val imageFile = uriToFile(uri, requireContext())
            Log.d("Image File", "showImage: ${imageFile.path}")

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uploadImage(token, multipartBody).collect { response ->
                        response.onSuccess { credentials ->
                            setLoadingState(false)
                            val moveWithObjectIntent = Intent(requireContext(), ScanResultActivity::class.java)
                            moveWithObjectIntent.putExtra(ScanResultActivity.ARTICLE_DATA,
                                credentials.data
                            )
                            requireContext().startActivity(moveWithObjectIntent)
                        }
                        response.onFailure {
                            Snackbar.make(
                                binding!!.root,
                                getString(R.string.error_occurred_message),
                                Snackbar.LENGTH_SHORT
                            ).show()

                            setLoadingState(false)
                        }
                    }
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding?.apply {
            btnCamera.isEnabled = !isLoading
            btnGallery.isEnabled = !isLoading
            buttonScan.isEnabled = !isLoading

            viewLoading.animateVisibility(isLoading)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}