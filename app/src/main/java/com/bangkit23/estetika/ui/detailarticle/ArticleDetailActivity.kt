package com.bangkit23.estetika.ui.detailarticle

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit23.estetika.data.model.BatikArticle
import com.bangkit23.estetika.databinding.ActivityArticleDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleData = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ARTICLE, BatikArticle::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ARTICLE)
        }

        if (articleData != null) {
            Glide.with(this).load(articleData.image).into(binding.imageView9)
            binding.nameBatik.text = articleData.name
            binding.batikDesc.text = articleData.desc_batik
            binding.motif.text = articleData.arti_motif
            binding.provinceLoc.text = articleData.asal

            binding.buttonVideo.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articleData.link_proses_pembuatan))
                this.startActivity(webIntent)
            }
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}