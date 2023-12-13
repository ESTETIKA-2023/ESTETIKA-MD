package com.bangkit23.estetika.ui.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit23.estetika.adapter.ShopAdapter
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.databinding.FragmentMarketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ShopAdapter
    private val viewModel: MarketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShopAdapter()
        val glm = GridLayoutManager(requireContext(), 2)
        binding.rvShop.layoutManager = glm
        binding.rvShop.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
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
                                    Toast.makeText(requireContext(),"ada kesalahan", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}