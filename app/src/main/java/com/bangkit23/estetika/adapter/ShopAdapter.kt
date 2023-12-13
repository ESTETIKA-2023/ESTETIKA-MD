package com.bangkit23.estetika.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.estetika.data.model.BatikItem
import com.bangkit23.estetika.databinding.ItemShopBinding
import com.bumptech.glide.Glide

class ShopAdapter: ListAdapter<BatikItem, ShopAdapter.ShopViewHolder>(ShopDiffCallback()) {

    class ShopViewHolder(private val binding: ItemShopBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BatikItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.gambar)
                    .into(tvShopImage)
                tvShopTitle.text = item.nama
                tvShopPrice.text = item.harga
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
        holder.itemView.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(items.link))
            holder.itemView.context.startActivity(webIntent)
        }
    }

    class ShopDiffCallback : DiffUtil.ItemCallback<BatikItem>() {
        override fun areItemsTheSame(oldItem: BatikItem, newItem: BatikItem): Boolean {
            return oldItem.gambar == newItem.gambar
        }

        override fun areContentsTheSame(oldItem: BatikItem, newItem: BatikItem): Boolean {
            return oldItem == newItem
        }
    }
}