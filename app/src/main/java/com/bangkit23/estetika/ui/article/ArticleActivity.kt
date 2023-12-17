package com.bangkit23.estetika.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit23.estetika.adapter.ArticleAdapter
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.databinding.ActivityArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding

    private lateinit var adapter: ArticleAdapter
    private val viewModel: ArticleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArticleAdapter()
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvArticle.layoutManager = llm
        binding.rvArticle.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        uiState?.articleList?.let { resources ->
                            when(resources) {
                                is Resources.Success -> {
                                    adapter.submitList(resources.data)
                                }
                                is Resources.Loading -> {

                                }
                                else -> {
                                    Toast.makeText(this@ArticleActivity,"ada kesalahan", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}