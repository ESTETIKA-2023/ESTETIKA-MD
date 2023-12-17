package com.bangkit23.estetika.ui.market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit23.estetika.adapter.ShopAdapter
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.databinding.ActivityBatikShopBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BatikShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBatikShopBinding

    private lateinit var adapter: ShopAdapter
    private val viewModel: MarketViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBatikShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ShopAdapter()
        val glm = GridLayoutManager(this, 2)
        binding.rvShop.layoutManager = glm
        binding.rvShop.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        uiState?.shopList?.let { resources ->
                            when(resources) {
                                is Resources.Success -> {
                                    adapter.submitList(resources.data)
                                }
                                is Resources.Loading -> {

                                }
                                else -> {
                                    Toast.makeText(this@BatikShopActivity,"ada kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}