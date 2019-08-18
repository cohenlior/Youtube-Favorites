package com.example.android.youtubefavorites.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.youtubefavorites.database.DatabaseVideo
import com.example.android.youtubefavorites.database.VideoDatabaseDao
import com.example.android.youtubefavorites.domain.Video
import com.example.android.youtubefavorites.network.YoutubeApi
import kotlinx.coroutines.*

enum class YouTubeApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [SearchFragment].
 */
class SearchViewModel(
    val database: VideoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<YouTubeApiStatus>()
    val status: LiveData<YouTubeApiStatus>
        get() = _status

    private val _results = MutableLiveData<List<Video>>()
    val results: LiveData<List<Video>>
        get() = _results

    private val _addToFavorite = MutableLiveData<Boolean>()
    val addToFavorite: LiveData<Boolean>
        get() = _addToFavorite
    /**
     * Sets the value of the status LiveData to the YouTube API status.
     */
    fun getResults(search: String) {
        uiScope.launch {
            val getResultsDeffered = YoutubeApi.retrofitService.getSearchResults(
                YoutubeApi.PROPERTY,
                YoutubeApi.RESULTS_PER_PAGE,
                search,
                YoutubeApi.RESOURCE_TYPE,
                YoutubeApi.API_KEY
            )
            try {
                _status.value = YouTubeApiStatus.LOADING

                val listResult = getResultsDeffered.await()
                _status.value = YouTubeApiStatus.DONE
                _results.value = listResult.videos
            } catch (e: Exception) {
                _status.value = YouTubeApiStatus.ERROR
                _results.value = ArrayList()
            }
        }
    }

    fun onFavoriteClicked(video: Video) {
        uiScope.launch {
            insert(video)
        }
    }

    fun onFavoriteClickedComplete() {
        _addToFavorite.value = null
    }

    private suspend fun insert(video: Video) {
        withContext(Dispatchers.IO) {
            val isVideoExist = database.get(video.videoId) == null
            if (isVideoExist) {
                database.insert(
                    DatabaseVideo(
                        videoId = video.id.videoId,
                        title = video.title, date = video.date, url = video.url
                    )
                )

                _addToFavorite.postValue(true)
            } else {
                _addToFavorite.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}