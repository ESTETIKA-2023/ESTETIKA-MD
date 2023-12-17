package com.bangkit23.estetika.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit23.estetika.adapter.ArticleAdapter
import com.bangkit23.estetika.adapter.ShopAdapter
import com.bangkit23.estetika.data.Resources
import com.bangkit23.estetika.databinding.FragmentHomeBinding
import com.bangkit23.estetika.ui.article.ArticleActivity
import com.bangkit23.estetika.ui.auth.AuthActivity
import com.bangkit23.estetika.ui.destination.BatikTourismActivity
import com.bangkit23.estetika.ui.market.BatikShopActivity
import com.bangkit23.estetika.ui.market.MarketViewModel
import com.bangkit23.estetika.ui.scanresult.ScanResultActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var token: String = ""
    private lateinit var adapter: ShopAdapter

    private val viewModel: HomeViewModel by viewModels()
    private val shopViewModel: MarketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAuthToken().observe(viewLifecycleOwner) { Token ->
            if (Token != null) {
                if (Token.isEmpty()) {
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                } else {
                    token = Token
                }
            } else {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
        }

        adapter = ShopAdapter()
        val glm = GridLayoutManager(requireContext(), 2)
        this.binding.recyclerView.layoutManager = glm
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    shopViewModel.uiState.collect { uiState ->
                        uiState?.shopList?.let { resources ->
                            when(resources) {
                                is Resources.Success -> {
                                    adapter.submitList(resources.data?.subList(0, 4))
                                }
                                is Resources.Loading -> {

                                }
                                else -> {
                                    Toast.makeText(requireContext(),"ada kesalahan", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.viewArticle.setOnClickListener {
            val intent = Intent(requireActivity(), ArticleActivity::class.java)

            startActivity(intent)
        }

        binding.viewShop.setOnClickListener {
            val intent = Intent(requireActivity(), BatikShopActivity::class.java)

            startActivity(intent)
        }

        binding.viewTourism.setOnClickListener {
            val intent = Intent(requireActivity(), BatikTourismActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}