package com.bangkit23.estetika.ui.destination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit23.estetika.adapter.TourismAdapter
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.databinding.ActivityBatikTourismBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BatikTourismActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBatikTourismBinding
    private lateinit var adapter: TourismAdapter

    private val viewModel: BatikDestinationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBatikTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TourismAdapter()
        val llm = GridLayoutManager(this, 2)
        binding.rvTourism.layoutManager = llm
        binding.rvTourism.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        uiState?.tourismList?.let { resources ->
                            when(resources) {
                                is Resources.Success -> {
                                    adapter.submitList(resources.data)
                                }
                                is Resources.Loading -> {

                                }
                                else -> {
                                    Toast.makeText(this@BatikTourismActivity,"ada kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}