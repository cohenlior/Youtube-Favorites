package com.example.android.youtubefavorites

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.youtubefavorites.domain.Video
import com.example.android.youtubefavorites.favorites.FavoriteListAdapter
import com.example.android.youtubefavorites.search.ResultListAdapter
import com.example.android.youtubefavorites.search.YouTubeApiStatus

@BindingAdapter("listData")
fun bindRecyclerViewResults(recyclerView: RecyclerView, data: List<Video>?) {
    val adapter = recyclerView.adapter as ResultListAdapter
    if (data != null) {
        adapter.data = data
    }

}

@BindingAdapter("favoriteData")
fun bindRecyclerViewFavorites(recyclerView: RecyclerView, data: List<Video>?) {
    val adapter = recyclerView.adapter as FavoriteListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)

    }
}

@BindingAdapter("youTubeApiStatus")
fun bindStatus(statusImageView: ImageView, status: YouTubeApiStatus?) {
    when (status) {
        YouTubeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        YouTubeApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        YouTubeApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}