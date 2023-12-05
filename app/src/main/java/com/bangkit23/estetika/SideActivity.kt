package com.bangkit23.estetika

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bangkit23.estetika.ml.ModelBatik
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

@AndroidEntryPoint
@Suppress("DEPRECATION")
class SideActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side)

        imageView = findViewById(R.id.imageView2)

        val fileName = "labels_batik.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use{it.readText()}
        var townlist = inputString.split("\n")

        var textView: TextView = findViewById(R.id.textView)


        var select: Button = findViewById(R.id.select)
        select.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 100)

        }

        var predict: Button = findViewById(R.id.predict)
        predict.setOnClickListener {
            val model = ModelBatik.newInstance(this)
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
            textView.text = townlist[max]
// Releases model resources if no longer used.
            model.close()
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


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imageView.setImageURI(data?.data)

        var uri: Uri? = data?.data
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
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
}