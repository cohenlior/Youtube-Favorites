package com.example.android.youtubefavorites.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.youtubefavorites.databinding.ListItemVideoFavoriteBinding
import com.example.android.youtubefavorites.databinding.ListItemVideoResultBinding
import com.example.android.youtubefavorites.domain.Video

class FavoriteListAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Video, FavoriteListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        holder.binding.favoriteIcon.setOnClickListener {
            clickListener.onClickFavorite(video)
        }
        holder.binding.shareIcon.setOnClickListener {
            clickListener.onClickShare(video)
        }
        holder.bind(video)
    }

    class ViewHolder private constructor(val binding: ListItemVideoFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Video) {
            binding.video = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ListItemVideoFavoriteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(view)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.tableId === newItem.tableId
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickListener {
        fun onClickFavorite(video: Video) {}
        fun onClickShare(video: Video) {}
    }
}