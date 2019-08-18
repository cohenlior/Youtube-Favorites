package com.example.android.youtubefavorites.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.youtubefavorites.R
import com.example.android.youtubefavorites.databinding.ListItemVideoResultBinding
import com.example.android.youtubefavorites.domain.Video

class ResultListAdapter(private val clickListener: OnClickListener) :
    RecyclerView.Adapter<ResultListAdapter.ViewHolder>() {

    var data = listOf<Video>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.favoriteIcon.setOnClickListener {
            clickListener.onClickFavorite(item)
        }
        holder.binding.videoThumbnail.setOnClickListener {
            ViewCompat.setTransitionName(holder.binding.videoThumbnail, it.resources.getString(R.string.transition_image))
            clickListener.onClickThumbnail(item, holder.binding.videoThumbnail)
        }
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemVideoResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Video) {
            binding.video = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ListItemVideoResultBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(view)
            }
        }
    }

    interface OnClickListener {
        fun onClickFavorite(video: Video) {}
        fun onClickThumbnail(video: Video, imageView: ImageView) {}
    }
}
