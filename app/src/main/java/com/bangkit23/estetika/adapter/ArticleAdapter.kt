package com.bangkit23.estetika.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit23.estetika.data.model.BatikArticle
import com.bangkit23.estetika.databinding.ItemArticleBinding
import com.bumptech.glide.Glide

class ArticleAdapter: ListAdapter<BatikArticle, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articles = getItem(position)
        holder.bind(articles)
    }

    class ArticleViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: BatikArticle) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.image)
                    .into(tvArticleImage)
                tvArticleTitle.text = article.name
                tvArticleDesc.text = article.desc_batik
            }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<BatikArticle>() {
        override fun areItemsTheSame(oldItem: BatikArticle, newItem: BatikArticle): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BatikArticle, newItem: BatikArticle): Boolean {
            return oldItem == newItem
        }
    }
}