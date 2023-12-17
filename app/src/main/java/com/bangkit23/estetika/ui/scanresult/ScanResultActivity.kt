package com.bangkit23.estetika.ui.scanresult

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit23.estetika.adapter.ShopRecommendAdapter
import com.bangkit23.estetika.adapter.TourismRecommendAdapter
import com.bangkit23.estetika.data.remote.response.FileData
import com.bangkit23.estetika.databinding.ActivityScanResultBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanResultBinding
    private lateinit var shopAdapter: ShopRecommendAdapter
    private lateinit var tourismAdapter: TourismRecommendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(ARTICLE_DATA, FileData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(ARTICLE_DATA)
        }

        shopAdapter = ShopRecommendAdapter()
        val glm = GridLayoutManager(this, 3)
        binding.rvResultRecom.layoutManager = glm
        binding.rvResultRecom.adapter = shopAdapter

        tourismAdapter = TourismRecommendAdapter()
        val slm = GridLayoutManager(this, 3)
        binding.rvResultRecomPlace.layoutManager = slm
        binding.rvResultRecomPlace.adapter = tourismAdapter

        if (articleData != null){
            if (articleData.firestoreArticleData?.get(0)?.image != null) {
                Glide.with(this).load(articleData.firestoreArticleData!![0].image).into(binding.imageView9)
            }

            if (articleData.firestoreArticleData?.get(0)?.linkProsesPembuatan != null) {
                binding.buttonVideo.setOnClickListener {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articleData.firestoreArticleData!![0].linkProsesPembuatan))
                    this.startActivity(webIntent)
                }
            }

            binding.nameBatik.text = articleData.firestoreArticleData?.get(0)?.name ?: "Bukan Batik"
            binding.batikDesc.text = articleData.firestoreArticleData?.get(0)?.descBatik ?: ""
            binding.motif.text = articleData.firestoreArticleData?.get(0)?.artiMotif ?: ""
            binding.provinceLoc.text = articleData.firestoreArticleData?.get(0)?.asal ?: ""

            if (articleData.firestoreBatikShopRecommendationData != null) {
                shopAdapter.submitList(articleData.firestoreBatikShopRecommendationData)
            }

            if (articleData.firestoreBatikTouristAttractionData != null) {
                tourismAdapter.submitList(articleData.firestoreBatikTouristAttractionData)
            }

            if (articleData.firestoreBatikTouristAttractionData?.isEmpty() == true) {
                binding.tvRelatedPlace.text = ""
            }
        }

    }

    companion object {
        const val ARTICLE_DATA = "article_data"
    }
}