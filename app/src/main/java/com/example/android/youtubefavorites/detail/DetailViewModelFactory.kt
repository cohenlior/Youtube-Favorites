package com.example.android.youtubefavorites.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.youtubefavorites.domain.Video

class DetailViewModelFactory(
    private val video: Video,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(video, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}