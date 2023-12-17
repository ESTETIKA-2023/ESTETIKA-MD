package com.bangkit23.estetika.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.estetika.R
import com.bangkit23.estetika.data.remote.response.FirestoreBatikShopRecommendationData
import com.bangkit23.estetika.databinding.ItemShopBinding
import com.bumptech.glide.Glide

class ShopRecommendAdapter: ListAdapter<FirestoreBatikShopRecommendationData, ShopRecommendAdapter.ShopMViewHolder>(ShopDiffCallback()) {

    class ShopMViewHolder(private val binding: ItemShopBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FirestoreBatikShopRecommendationData) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.gambar)
                    .into(tvShopImage)
                tvShopTitle.text = item.nama
                tvShopPrice.text = item.harga
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopMViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopMViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopMViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
        holder.itemView.findViewById<Button>(R.id.tvShopBuy).setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(items.link))
            holder.itemView.context.startActivity(webIntent)
        }
    }

    class ShopDiffCallback : DiffUtil.ItemCallback<FirestoreBatikShopRecommendationData>() {
        override fun areItemsTheSame(oldItem: FirestoreBatikShopRecommendationData, newItem: FirestoreBatikShopRecommendationData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FirestoreBatikShopRecommendationData, newItem: FirestoreBatikShopRecommendationData): Boolean {
            return oldItem == newItem
        }
    }
}