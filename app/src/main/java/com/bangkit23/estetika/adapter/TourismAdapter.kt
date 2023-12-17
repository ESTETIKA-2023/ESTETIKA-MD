package com.bangkit23.estetika.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.estetika.data.model.BatikTourism
import com.bangkit23.estetika.databinding.ItemTourismBinding
import com.bumptech.glide.Glide

class TourismAdapter : ListAdapter<BatikTourism, TourismAdapter.TourismViewHolder>(TourismDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourismViewHolder {
        val binding = ItemTourismBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourismViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourismViewHolder, position: Int) {
        val tourism = getItem(position)
        holder.bind(tourism)
        holder.itemView.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(tourism.moreInfo))
            holder.itemView.context.startActivity(webIntent)
        }
    }

    class TourismViewHolder(private val binding: ItemTourismBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourism: BatikTourism) {
            binding.apply {
                Glide.with(itemView)
                    .load(tourism.image)
                    .into(rvTourismImage)
                tvTourismTitle.text = tourism.name
                tvTourismLocation.text = tourism.province
                tvTourismPrice.text = tourism.price
            }
        }
    }

    class TourismDiffCallback : DiffUtil.ItemCallback<BatikTourism>() {
        override fun areItemsTheSame(oldItem: BatikTourism, newItem: BatikTourism): Boolean {
            // Replace this with your own logic
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BatikTourism, newItem: BatikTourism): Boolean {
            // Replace this with your own logic
            return oldItem == newItem
        }
    }
}
