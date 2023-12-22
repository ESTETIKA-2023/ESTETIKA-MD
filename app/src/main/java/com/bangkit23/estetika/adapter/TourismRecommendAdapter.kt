package com.bangkit23.estetika.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.estetika.R
import com.bangkit23.estetika.data.remote.response.FirestoreBatikTouristAttractionData
import com.bangkit23.estetika.databinding.ItemTourismBinding
import com.bumptech.glide.Glide

class TourismRecommendAdapter : ListAdapter<FirestoreBatikTouristAttractionData, TourismRecommendAdapter.TourismRViewHolder>(TourismDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourismRViewHolder {
        val binding = ItemTourismBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TourismRViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourismRViewHolder, position: Int) {
        val tourism = getItem(position)
        holder.bind(tourism)
        holder.itemView.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(tourism.moreInfo))
            holder.itemView.context.startActivity(webIntent)
        }
        holder.itemView.findViewById<Button>(R.id.tvTourismBuy).setOnClickListener {
            val whatsAppIntent = Intent(Intent.ACTION_VIEW)
            // Replace with the country code and phone number of the shop
            whatsAppIntent.data = Uri.parse(tourism.waLink)
            if (whatsAppIntent.resolveActivity(holder.itemView.context.packageManager) != null) {
                holder.itemView.context.startActivity(whatsAppIntent)
            } else {
                Toast.makeText(holder.itemView.context, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class TourismRViewHolder(private val binding: ItemTourismBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourism: FirestoreBatikTouristAttractionData) {
            binding.apply {
                Glide.with(itemView)
                    .load(tourism.image)
                    .into(rvTourismImage)
                tvTourismTitle.text = tourism.namaAtraksi
                tvTourismLocation.text = tourism.province
                tvTourismPrice.text = tourism.price
            }
        }
    }

    class TourismDiffCallback : DiffUtil.ItemCallback<FirestoreBatikTouristAttractionData>() {
        override fun areItemsTheSame(oldItem: FirestoreBatikTouristAttractionData, newItem: FirestoreBatikTouristAttractionData): Boolean {
            // Replace this with your own logic
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FirestoreBatikTouristAttractionData, newItem: FirestoreBatikTouristAttractionData): Boolean {
            // Replace this with your own logic
            return oldItem == newItem
        }
    }
}
