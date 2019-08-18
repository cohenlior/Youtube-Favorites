package com.example.android.youtubefavorites.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.youtubefavorites.domain.Video

class DetailViewModel(video: Video, application: Application) : AndroidViewModel(application) {

    private val _selectedVideo = MutableLiveData<Video>()

    val selectedVideo: LiveData<Video>
        get() = _selectedVideo

    init {
        _selectedVideo.value = video
    }

}